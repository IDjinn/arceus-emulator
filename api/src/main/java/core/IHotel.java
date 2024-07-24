package core;

import core.concurrency.IProcessHandler;
import core.events.IEventHandler;

public interface IHotel extends IHotelService {
    IProcessHandler getProcessHandler();

    IEventHandler getEventHandlerManager();
}
