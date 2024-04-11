package core;

import core.concurrency.IProcessHandler;

public interface IHotel  {
    IProcessHandler getProcessHandler();
    
    void init();

    void shutdown();
}
