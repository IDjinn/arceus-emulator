package core;

import core.concurrency.IProcessHandler;
import core.events.IEventHandlerManager;

public interface IHotel extends IHotelService {
    IProcessHandler getProcessHandler();

    IEventHandlerManager getEventHandlerManager();
}
