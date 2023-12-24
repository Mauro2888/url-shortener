package com.url.shortener.common.transactional;

import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

public interface AsyncRunner {

    <U> CompletionStage<U> supplyAsync(Supplier<U> supplier);
    CompletionStage<Void>runSync(Runnable runnable);
}
