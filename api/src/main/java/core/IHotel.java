package core;

import core.concurrency.IProcessHandler;

public interface IHotel extends IHotelService {
    IProcessHandler getProcessHandler();
}
