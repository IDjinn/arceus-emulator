package habbo.navigator.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.configuration.IConfigurationManager;
import habbo.habbos.IHabbo;
import habbo.navigator.INavigatorManager;
import habbo.navigator.INavigatorRoomsProvider;
import habbo.navigator.tabs.INavigatorTab;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class NavigatorSearchService implements INavigatorSearchService {
    private final Executor executor;

    private final INavigatorManager navigatorManager;

    private final INavigatorRoomsProvider navigatorRoomsProvider;

    @Inject
    public NavigatorSearchService(
            IConfigurationManager configurationManager,
            INavigatorManager navigatorManager,
            INavigatorRoomsProvider navigatorRoomsProvider
    ) {
        this.navigatorManager = navigatorManager;
        this.navigatorRoomsProvider = navigatorRoomsProvider;

        this.executor = Executors.newScheduledThreadPool(
                configurationManager.getInt("hotel.navigator.threads", 2)
        );
    }

    public void commit(IHabbo habbo, String tabName, String query) {
        this.executor.execute(() -> {
            if (!query.isBlank()) {
                this.search(habbo, tabName, query);
                return;
            }

            final INavigatorTab tab = this.navigatorManager.getTab(tabName);

            if(tab == null) {
                this.sendRoomsFromCategory(habbo, tabName, query);
                return;
            }
        });
    }

    private void sendRoomsFromCategory(IHabbo habbo, String tabName, String query) {
        // Send rooms from category
    }

    private void search(IHabbo habbo, String view, String query) {
        // Search for rooms
    }
}
