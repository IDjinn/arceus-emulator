package configuration;

import org.jetbrains.annotations.NotNull;

public interface IConfigurationManager {
    void configureFromFile(@NotNull String fileName);
    public boolean getBool(@NotNull String key);
    public boolean getBool(@NotNull String key, boolean defaultValue);
    
    public String getString(@NotNull String key);
    public String getString(@NotNull String key, String defaultValue);
    
    public int getInt(@NotNull String key);
    public int getInt(@NotNull String key, int defaultValue);
    
    public long getLong(@NotNull String key);
    public long getLong(@NotNull String key, long defaultValue);
    
    
    public double getDouble(@NotNull String key);
        public double getDouble(@NotNull String key, double defaultValue);

}
