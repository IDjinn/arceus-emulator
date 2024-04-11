package core;

import java.util.concurrent.ScheduledExecutorService;

public interface IThreadManager {
    ScheduledExecutorService getHardwareThreadExecutor();

    ScheduledExecutorService getSoftwareThreadExecutor();
}
