package core.plugins;

import core.IHotelService;

public interface IPlugin extends IHotelService {
    String getName();

    String getDescription();

    Runtime.Version getVersion();

    String getAuthor();
}
