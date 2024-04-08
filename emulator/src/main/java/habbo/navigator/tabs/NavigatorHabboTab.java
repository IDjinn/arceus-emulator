package habbo.navigator.tabs;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.navigator.INavigatorRoomsProvider;
import habbo.navigator.data.INavigatorFilterType;
import habbo.navigator.data.INavigatorResultCategory;
import habbo.navigator.data.NavigatorResultCategory;
import habbo.navigator.enums.NavigatorDisplayOrder;
import habbo.navigator.enums.NavigatorListAction;
import habbo.rooms.IRoom;
import habbo.rooms.data.IRoomCategory;

import java.util.ArrayList;
import java.util.List;

public class NavigatorHabboTab implements INavigatorTab {
    public final static String FILTER_NAME = "myworld_view";

    @Inject
    private INavigatorRoomsProvider navigatorRoomsProvider;

    private final String[] categories = new String[] {
            "my",
            "favorites",
            "history_freq",
            "my_groups",
            "with_friends",
            "with_rights"
    };

    public List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo) {
        int order = 0;
        final List<INavigatorResultCategory> categories = new ArrayList<>();

        for(String category : this.categories) {
            final List<IRoom> categoryRooms = this.navigatorRoomsProvider.getRoomsForCategory(category, habbo);

            categories.add(new NavigatorResultCategory(
                    order,
                    category,
                    "",
                    NavigatorListAction.NONE,
                    habbo.getNavigator().getDisplayModeForCategory(category),
                    habbo.getNavigator().getLayoutDisplayForCategory(category),
                    categoryRooms,
                    true,
                    true, // TODO: Should I show invisible rooms for all categories?
                    NavigatorDisplayOrder.ORDER_NUMERICAL,
                    order
            ));

            order++;
        }

        return categories;
    }

    @Override
    public List<INavigatorResultCategory> getSearchedResultForHabbo(
            IHabbo habbo,
            INavigatorFilterType filterType,
            String search,
            IRoomCategory category
    ) {
        return null;
    }
}
