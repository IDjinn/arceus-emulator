package core.configuration;

import java.util.HashMap;

public interface IEmulatorSettings {
    void init();

    void reload();

    HashMap<String, String> getSettings();

    String getOrDefault(String key, String defaultValue);
}
