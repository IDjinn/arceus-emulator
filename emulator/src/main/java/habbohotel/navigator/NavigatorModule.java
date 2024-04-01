package habbohotel.navigator;

import com.google.inject.AbstractModule;

public class NavigatorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(INavigatorManager.class).to(NavigatorManager.class);
    }
}
