package core.configuration;

import org.jetbrains.annotations.NotNull;

public interface IConfigurationManager {
    boolean getBool(@NotNull String key);

    boolean getBool(@NotNull String key, boolean defaultValue);

    String getString(@NotNull String key);

    String getString(@NotNull String key, String defaultValue);

    int getInt(@NotNull String key);

    int getInt(@NotNull String key, int defaultValue);

    long getLong(@NotNull String key);

    long getLong(@NotNull String key, long defaultValue);


    double getDouble(@NotNull String key);

    double getDouble(@NotNull String key, double defaultValue);

}
