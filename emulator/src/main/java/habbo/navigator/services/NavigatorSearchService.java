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
import habbo.rooms.IRoomManager;
import habbo.rooms.data.IRoomCategory;
import packets.outgoing.navigator.search.NewNavigatorSearchResultsComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class NavigatorSearchService implements INavigatorSearchService {
    private final Executor executor;

    private final INavigatorManager navigatorManager;

    private final IRoomManager roomManager;

    private final INavigatorRoomsProvider navigatorRoomsProvider;

    @Inject
    public NavigatorSearchService(
            IConfigurationManager configurationManager,
            INavigatorManager navigatorManager,
            INavigatorRoomsProvider navigatorRoomsProvider,
            IRoomManager roomManager
    ) {
        this.roomManager = roomManager;
        this.navigatorManager = navigatorManager;
        this.navigatorRoomsProvider = navigatorRoomsProvider;

        this.executor = Executors.newScheduledThreadPool(
                configurationManager.getInt("hotel.navigator.threads", 2)
        );
    }

    public void commit(IHabbo habbo, String tabName, String query) {
        this.executor.execute(() -> {
            final INavigatorTab tab = this.navigatorManager.getTab(tabName);

            if(tab == null) {
                this.sendRoomsFromCategory(habbo, tabName, query);
                return;
            }

            if(query.isBlank()) {
                habbo.getClient().sendMessage(new NewNavigatorSearchResultsComposer(
                        tabName, query, tab.getResultForHabbo(habbo)
                ));
                return;
            }

            INavigatorFilterType filterType = this.navigatorManager.getFilterTypeByKey("anything");

            if(filterType == null) return;

            final String[] parsedQuery = query.split(":");
            final IRoomCategory category = this.roomManager.getCategoryFromTab(tabName);

            if(parsedQuery.length <= 1) {
                habbo.getClient().sendMessage(new NewNavigatorSearchResultsComposer(
                        tabName, query, tab.getSearchedResultForHabbo(habbo, filterType, parsedQuery[0], category)
                ));
                return;
            }

            filterType = this.navigatorManager.getFilterTypeByKey(parsedQuery[0].replace(":", ""));

            if(filterType == null) return;

            habbo.getClient().sendMessage(new NewNavigatorSearchResultsComposer(
                    tabName, query, tab.getSearchedResultForHabbo(habbo, filterType, parsedQuery[1], category)
            ));
        });
    }

    private void sendRoomsFromCategory(IHabbo habbo, String tabName, String query) {
        final List<IRoom> rooms = this.navigatorRoomsProvider.getRoomsForCategory(tabName, habbo);

        final List<INavigatorResultCategory> categories = new ArrayList<>() {
            {
                this.add(new NavigatorResultCategory(0, tabName, query, NavigatorListAction.NONE, NavigatorDisplayMode.LIST, NavigatorLayoutDisplay.DEFAULT, rooms, false, false, NavigatorDisplayOrder.ACTIVITY, -1));
            }
        };

        habbo.getClient().sendMessage(new NewNavigatorSearchResultsComposer(tabName, query, categories));
    }
}
