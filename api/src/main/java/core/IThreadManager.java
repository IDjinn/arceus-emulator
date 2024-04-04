package core;

import java.util.concurrent.ScheduledExecutorService;

public interface IThreadManager {
    public ScheduledExecutorService getHardwareThreadExecutor();

    public ScheduledExecutorService getSoftwareThreadExecutor();
}
