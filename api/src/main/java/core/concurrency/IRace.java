package core.concurrency;

import stormpot.Timeout;

import java.util.Optional;
import java.util.concurrent.Callable;

public interface IRace {
    <T> Optional<T> futureWithTimeout(Callable<T> future, Timeout timeout);

    <T> void runnableWithTimeout(
            Runnable runnable,
            Timeout timeout
    );
}
