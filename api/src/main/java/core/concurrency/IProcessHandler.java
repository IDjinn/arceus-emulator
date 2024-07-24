package core.concurrency;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public interface IProcessHandler {
    void registerProcess(@NotNull String key, Runnable runnable, int initialDelay, long interval, TimeUnit timeUnit);

    void registerProcess(@NotNull String key, Runnable runnable, long interval, TimeUnit timeUnit);

    boolean unregisterProcess(@NotNull String key);

    void unregisterAllProcess();
}
