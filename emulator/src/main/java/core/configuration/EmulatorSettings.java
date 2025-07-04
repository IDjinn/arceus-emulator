package core.configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.repositories.emulator.IEmulatorRepository;

import java.util.HashMap;

@Singleton
public class EmulatorSettings implements IEmulatorSettings {
    private final Logger logger = LogManager.getLogger();

    @Inject
    private IEmulatorRepository emulatorRepository;

    private boolean isLoaded = false;

    @Getter
    private HashMap<String, String> settings;

    public void init() {
        this.loadAllSettings(false);
    }

    @Override
    public void destroy() {
        
    }

    private void loadAllSettings(boolean forceReload) {
        if(this.isLoaded && !forceReload) return;

        this.settings = new HashMap<>();

        this.emulatorRepository.loadAllSettings(result -> {
            if(result == null) return;

            this.settings.put(result.getString("key"), result.getString("value"));
        });

        this.logger.info(
                "Loaded {} emulator settings from database.",
                this.settings.size()
        );

        this.isLoaded = true;
    }

    public void reload() {
        this.loadAllSettings(true);
    }

    public String getOrDefault(String key, String defaultValue) {
        return this.settings.getOrDefault(key, defaultValue);
    }
}
