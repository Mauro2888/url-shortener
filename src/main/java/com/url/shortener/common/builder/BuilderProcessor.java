package com.url.shortener.common.builder;

import com.google.auto.service.AutoService;
import jakarta.enterprise.context.ApplicationScoped;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedSourceVersion(SourceVersion.RELEASE_17)
@SupportedAnnotationTypes("com.url.shortener.common.builder.Builder")
@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.forEach(annotation ->
                roundEnv.getElementsAnnotatedWith(annotation)
                        .forEach(this::generateBuilderFile));
        return true;
    }

    private void generateBuilderFile(Element element) {
        if (!(element instanceof TypeElement)) return;
        TypeElement typeElement = (TypeElement) element;

        String className = typeElement.getSimpleName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        String builderName = className + "Builder";

        try (PrintWriter writer = new PrintWriter(
                processingEnv.getFiler().createSourceFile(packageName + "." + builderName).openWriter())) {

            writer.println("package " + packageName + ";");
            writer.println("public class " + builderName + " {");

            // Generating fields
            typeElement.getEnclosedElements().stream()
                    .filter(e -> e.getKind() == ElementKind.FIELD)
                    .forEach(field -> {
                        writer.println("    private " + field.asType() + " " + field.getSimpleName() + ";");
                    });

            writer.println();

            // Generating builder methods
            typeElement.getEnclosedElements().stream()
                    .filter(e -> e.getKind() == ElementKind.FIELD)
                    .forEach(field -> {
                        writer.println("    public " + builderName + " " + field.getSimpleName() + "(" + field.asType() + " value) {");
                        writer.println("        this." + field.getSimpleName() + " = value;");
                        writer.println("        return this;");
                        writer.println("    }");
                    });

            // Generating build method
            writer.println("    public " + className + " build() {");
            writer.println("        return new " + className + "(" +
                    typeElement.getEnclosedElements().stream()
                            .filter(e -> e.getKind() == ElementKind.FIELD)
                            .map(Element::getSimpleName)
                            .collect(Collectors.joining(", ")) +
                    ");");
            writer.println("    }");

            writer.println("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
