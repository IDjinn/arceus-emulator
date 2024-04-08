package habbo.navigator;

import com.google.inject.AbstractModule;
import habbo.navigator.services.INavigatorSearchService;
import habbo.navigator.services.NavigatorSearchService;

public class NavigatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(INavigatorManager.class).to(NavigatorManager.class);
        bind(INavigatorSearchService.class).to(NavigatorSearchService.class);
        bind(INavigatorRoomsProvider.class).to(NavigatorRoomsProvider.class);
    }
}
