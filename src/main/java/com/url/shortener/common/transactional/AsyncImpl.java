package com.url.shortener.common.transactional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;


@ApplicationScoped
public class AsyncImpl implements AsyncRunner {

    private final ManagedExecutor executor;
    @Inject
    public AsyncImpl(ManagedExecutor executor) {
        this.executor = executor;
    }

    @Override
    @Transactional
    public <U> CompletionStage<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    @Override
    @Transactional
    public CompletionStage<Void> runSync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executor);
    }
}
