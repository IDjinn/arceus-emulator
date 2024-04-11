package habbo;

import com.google.inject.Inject;
import core.IHotel;
import core.concurrency.IProcessHandler;

public class Hotel implements IHotel {
    private final IProcessHandler processHandler;

    @Inject
    public Hotel(IProcessHandler processHandler) {
        this.processHandler = processHandler;
    }

    @Override
    public IProcessHandler getProcessHandler() {
        return this.processHandler;
    }

    @Override
    public void init() {
        
    }

    @Override
    public void shutdown() {

    }
}
