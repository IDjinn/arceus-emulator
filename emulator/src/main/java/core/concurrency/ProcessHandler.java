package core.concurrency;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.ReflectionHelpers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ProcessHandler implements IProcessHandler {
    private final Logger logger = LogManager.getLogger();
    private final Map<String, ScheduledFuture<?>> processes;
    private final IThreadManager threadManager;

    @Inject
    public ProcessHandler(IThreadManager threadManager) {
        this.threadManager = threadManager;
        this.processes = new ConcurrentHashMap<>();
    }

    @Override
    public void registerProcess(@NotNull final String key, final Runnable runnable, final int initialDelay, final long interval, final TimeUnit timeUnit) {
        assert initialDelay >= 0;
        assert interval > 0 : "interval must be greater than 0 (it's a bug or a typo?)";

        if (this.processes.containsKey(key))
            throw new IllegalStateException("already registered");

        this.processes.put(key, this.threadManager.getSoftwareThreadExecutor().scheduleAtFixedRate(runnable,
                initialDelay,
                interval,
                timeUnit));
        this.logger.debug("registered process {} with interval {} {} from class {}", key, interval,
                timeUnit.toString().toLowerCase(),
                ReflectionHelpers.getCallerInfo());
    }

    @Override
    public void registerProcess(@NotNull String key, Runnable runnable, long interval, TimeUnit timeUnit) {
        this.registerProcess(key, runnable, 0, interval, timeUnit);
    }

    @Override
    public boolean unregisterProcess(@NotNull String key) {
        var process = this.processes.get(key);
        if (process == null)
            return false;

        return process.cancel(true);
    }

    public void unregisterAllProcess() {
        for (var key : this.processes.keySet()) {
            try {
                this.unregisterProcess(key);
            } catch (Exception e) {
                this.logger.error("Unable to unregister process {} due exception {}", key, e.getMessage(), e);
            }
        }
    }
}
