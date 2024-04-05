package core.configuration;

import java.util.HashMap;

public interface IEmulatorSettings {
    void init();

    void reload();

    HashMap<String, String> getSettings();

    String get(String key);
}
