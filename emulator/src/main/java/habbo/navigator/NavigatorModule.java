package habbo.navigator;

import com.google.inject.AbstractModule;
import habbo.navigator.services.INavigatorSearchService;
import habbo.navigator.services.NavigatorSearchService;

public class NavigatorModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(INavigatorManager.class).to(NavigatorManager.class);
        this.bind(INavigatorSearchService.class).to(NavigatorSearchService.class);
        this.bind(INavigatorRoomsProvider.class).to(NavigatorRoomsProvider.class);
    }
}
