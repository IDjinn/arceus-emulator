package core.concurrency;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.configuration.IConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class ThreadManager implements IThreadManager {
    private final AtomicReference<ScheduledThreadPoolExecutor> hardwareThreadExecutor;
    private final IConfigurationManager configurationManager;
    private final AtomicReference<ScheduledThreadPoolExecutor> softwareThreadExecutor;
    private final long threadMonitorInterval;
    private final Logger logger = LogManager.getLogger();

    private final AtomicLong hardwareThreadCounter;
    private final AtomicLong softwareThreadCounter;
    private @Nullable AtomicReference<Executor> threadMonitor;

    @Inject
    public ThreadManager(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
        this.hardwareThreadCounter = new AtomicLong(0);
        this.softwareThreadCounter = new AtomicLong(0);
        this.threadMonitorInterval = this.configurationManager.getLong("orion.monitor.interval", 1_000L);
        assert this.threadMonitorInterval > 0;

        var hardwareThreadCount = this.configurationManager.getInt("orion.hardware.threads", Runtime.getRuntime().availableProcessors());
        var softwareThreadCount = this.configurationManager.getInt("orion.software.threads", Runtime.getRuntime().availableProcessors() * 200);
        assert hardwareThreadCount > 0;
        assert softwareThreadCount > 0;

        this.hardwareThreadExecutor = new AtomicReference<>(new ScheduledThreadPoolExecutor(hardwareThreadCount, runnable -> {
            final var currentId = this.hardwareThreadCounter.incrementAndGet();

            final var hardwareThread = new Thread(runnable);
            final var threadName = "Hardware-Thread-" + currentId;
            hardwareThread.setName(threadName);

            final var log = LogManager.getLogger(threadName);
            hardwareThread.setUncaughtExceptionHandler((t, e) -> log.error("Exception in hardware thread: {}", e.getMessage(), e));

            return hardwareThread;
        }));

        this.softwareThreadExecutor = new AtomicReference<>(new ScheduledThreadPoolExecutor(softwareThreadCount, Thread.ofVirtual().factory()));
        if (this.configurationManager.getBool("orion.monitor.enabled", false)) {
            this.threadMonitor = new AtomicReference<>(Executors.newSingleThreadExecutor());
            this.threadMonitor.get().execute(this::monitor);
        }
    }

    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    private void monitor() {
        while (true) {
            try {
                Thread.sleep(this.threadMonitorInterval);
            } catch (InterruptedException e) {
                this.logger.error("Thread monitor interrupted", e);
            }

            final var hardwareThreads = this.hardwareThreadExecutor.get().getQueue().size();
            final var softwareThreads = this.softwareThreadExecutor.get().getQueue().size();
            this.logger.debug("Hardware thread count: {}, Software thread count: {}", hardwareThreads, softwareThreads);
        }
    }

    @Override
    public ScheduledExecutorService getHardwareThreadExecutor() {
        return this.hardwareThreadExecutor.get();
    }

    @Override
    public ScheduledExecutorService getSoftwareThreadExecutor() {
        return this.softwareThreadExecutor.get();
    }
}
