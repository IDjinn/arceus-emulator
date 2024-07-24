package core.concurrency;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stormpot.Timeout;
import utils.ReflectionHelpers;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Singleton
public final class Race implements IRace {
    private static final Logger LOGGER = LogManager.getLogger();
    private final IThreadManager threadManager;
    private final boolean isDebugging = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().contains("-agentlib:jdwp");

    @Inject
    public Race(final IThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    @Override
    public <T> Optional<T> futureWithTimeout(final Callable<T> callable, final Timeout timeout) {
        try {
            final var future = this.threadManager.getSoftwareThreadExecutor().submit(callable);
            if (this.isDebugging)
                return Optional.of(future.get());

            return Optional.of(future.get(timeout.getTimeoutInBaseUnit(), timeout.getBaseUnit()));
        } catch (TimeoutException | InterruptedException | ExecutionException timeoutException) {
            LOGGER.trace("Timeout exception occurred while running {} from {}: {}",
                    callable,
                    ReflectionHelpers.getCallerInfo(),
                    timeoutException.getMessage(),
                    timeoutException);
            return Optional.empty();
        }
    }

    @Override
    public <T> void runnableWithTimeout(final Runnable runnable, final Timeout timeout) {
        try {
            final var future = this.threadManager.getSoftwareThreadExecutor().submit(runnable);
            if (this.isDebugging) // we need get result here if is debugging because if there is some breaking point 
                // can cause timeout in our task
                future.get();
            else
                future.get(timeout.getTimeoutInBaseUnit(), timeout.getBaseUnit());
        } catch (TimeoutException | InterruptedException | ExecutionException timeoutException) {
            LOGGER.trace("Timeout exception occurred while running {} from {}: {}",
                    runnable,
                    ReflectionHelpers.getCallerInfo(),
                    timeoutException.getMessage(),
                    timeoutException);
        }
    }
}
