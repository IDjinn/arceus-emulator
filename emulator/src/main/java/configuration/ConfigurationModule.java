package configuration;

import com.google.inject.AbstractModule;

public class ConfigurationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IEmulatorSettings.class).to(EmulatorSettings.class);
    }
}
