package habbo.navigator.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.configuration.IConfigurationManager;
import habbo.habbos.IHabbo;
import habbo.navigator.INavigatorManager;
import habbo.navigator.INavigatorRoomsProvider;
import habbo.navigator.data.INavigatorFilterType;
import habbo.navigator.data.INavigatorResultCategory;
import habbo.navigator.data.NavigatorResultCategory;
import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.navigator.enums.NavigatorDisplayOrder;
import habbo.navigator.enums.NavigatorLayoutDisplay;
import habbo.navigator.enums.NavigatorListAction;
import habbo.navigator.tabs.INavigatorTab;
import habbo.rooms.IRoom;
import packets.outgoing.navigator.search.NewNavigatorSearchResultsComposer;

import java.util.ArrayList;
import java.util.List;
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

            final INavigatorFilterType filterType = this.navigatorManager.getFilterTypeByKey("anything");

            if(filterType == null) return;

            final List<INavigatorResultCategory> categories = tab.getResultForHabbo(habbo);

            if(query.isBlank()) {
                habbo.getClient().sendMessage(new NewNavigatorSearchResultsComposer(tabName, query, categories));
                return;
            }
        });
    }

    private void sendRoomsFromCategory(IHabbo habbo, String tabName, String query) {
        final List<IRoom> rooms = this.navigatorRoomsProvider.getRoomFromCategory(tabName, habbo);

        final List<INavigatorResultCategory> categories = new ArrayList<>(){
            {
                add(new NavigatorResultCategory(0, tabName, query, NavigatorListAction.NONE, NavigatorDisplayMode.LIST, NavigatorLayoutDisplay.DEFAULT, rooms, false, false, NavigatorDisplayOrder.ACTIVITY, -1));
            }
        };

        habbo.getClient().sendMessage(new NewNavigatorSearchResultsComposer(tabName, query, categories));
    }

    private void search(IHabbo habbo, String view, String query) {
        // Search for rooms
    }
}
