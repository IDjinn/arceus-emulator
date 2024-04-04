package core.configuration;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Singleton
public class ConfigurationManager implements IConfigurationManager {
    private final Logger logger = LogManager.getLogger();
    private final Properties properties;
    
    public ConfigurationManager() {
        properties = new Properties();
        var inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        try {
            if (inputStream != null)
                properties.load(inputStream);
        } catch (FileNotFoundException ex) {
            logger.error("Configuration file config.properties was not found");
        } catch (IOException ex) {
            logger.error("Configuration file exception: {}", ex.getMessage(), ex);
        }
        
    }
    @Override
    public boolean getBool(@NotNull String key) {
            return properties.getProperty(key).equals("1");
    }

    @Override
    public boolean getBool(@NotNull String key, boolean defaultValue) {
            return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    @Override
    public String getString(@NotNull String key) {
        
        return properties.getProperty(key);
    }

    @Override
    public String getString(@NotNull String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public int getInt(@NotNull String key) {
      return Integer.parseInt(properties.getProperty(key));
    }

    @Override
    public int getInt(@NotNull String key, int defaultValue) {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    @Override
    public long getLong(@NotNull String key) {
        return Long.parseLong(properties.getProperty(key));
    }

    @Override
    public long getLong(@NotNull String key, long defaultValue) {
        return Long.parseLong(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    @Override
    public double getDouble(@NotNull String key) {
        return Double.parseDouble(properties.getProperty(key));
    }

    @Override
    public double getDouble(@NotNull String key, double defaultValue) {
        return Double.parseDouble(properties.getProperty(key, String.valueOf(defaultValue)));
    }
}
