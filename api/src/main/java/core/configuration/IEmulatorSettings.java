package core.configuration;

import core.IHotelService;

import java.util.HashMap;

public interface IEmulatorSettings extends IHotelService {
    void init();

    void reload();

    HashMap<String, String> getSettings();

    String getOrDefault(String key, String defaultValue);
}
