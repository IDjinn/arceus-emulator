package habbo.internationalization;

import com.google.inject.AbstractModule;

public class InternationalizationModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(IInternationalizationManager.class).to(InternationalizationManager.class);
    }
}
