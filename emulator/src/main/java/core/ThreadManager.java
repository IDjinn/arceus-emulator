package core;

import com.google.inject.Inject;
import core.configuration.IConfigurationManager;
import org.apache.logging.log4j.LogManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadManager implements IThreadManager {
    private final IConfigurationManager configurationManager;
    private final ScheduledExecutorService hardwareThreadExecutor;
    private final ScheduledExecutorService softwareThreadExecutor;
    private AtomicLong hardwareThreadCounter;
    private AtomicLong softwareThreadCounter;

    @Inject
    public ThreadManager(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
        this.hardwareThreadCounter = new AtomicLong(0);
        this.softwareThreadCounter = new AtomicLong(0);

        var hardwareThreadCount = this.configurationManager.getInt("orion.hardware.threads", Runtime.getRuntime().availableProcessors());
        var softwareThreadCount = this.configurationManager.getInt("orion.software.threads", Runtime.getRuntime().availableProcessors());
        this.hardwareThreadExecutor = Executors.newScheduledThreadPool(hardwareThreadCount, runnable -> {
            var currentId = hardwareThreadCounter.incrementAndGet();

            var hardwareThread = new Thread(runnable);
            hardwareThread.setName(STR."Hardware-Thread-\{currentId}");

            var log = LogManager.getLogger(STR."Hardware-Thread-\{currentId}");
            hardwareThread.setUncaughtExceptionHandler((t, e) -> log.error("Exception in hardware thread: {}", e.getMessage(), e));

            return hardwareThread;
        });
        this.softwareThreadExecutor = Executors.newScheduledThreadPool(softwareThreadCount, runnable -> {
            var currentId = softwareThreadCounter.incrementAndGet();

            var softwareThread = Thread.ofVirtual().factory().newThread(runnable);
            softwareThread.setName(STR."Software-Thread-\{currentId}");

            var log = LogManager.getLogger(STR."Software-Thread-\{currentId}");
            softwareThread.setUncaughtExceptionHandler((t, e) -> log.error("Exception in software thread {}", e.getMessage(), e));

            return softwareThread;
        });
    }

    @Override
    public ScheduledExecutorService getHardwareThreadExecutor() {
        return this.hardwareThreadExecutor;
    }

    @Override
    public ScheduledExecutorService getSoftwareThreadExecutor() {
        return this.softwareThreadExecutor;
    }
}
