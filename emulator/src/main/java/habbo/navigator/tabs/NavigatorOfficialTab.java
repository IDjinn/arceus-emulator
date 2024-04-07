package habbo.navigator.tabs;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.navigator.INavigatorManager;
import habbo.navigator.INavigatorRoomsProvider;
import habbo.navigator.data.INavigatorFilterType;
import habbo.navigator.data.INavigatorPublicCategory;
import habbo.navigator.data.INavigatorResultCategory;
import habbo.navigator.data.NavigatorResultCategory;
import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.navigator.enums.NavigatorDisplayOrder;
import habbo.navigator.enums.NavigatorLayoutDisplay;
import habbo.navigator.enums.NavigatorListAction;
import habbo.rooms.data.IRoomCategory;

import java.util.ArrayList;
import java.util.List;

public class NavigatorOfficialTab implements INavigatorTab {
    public final static String FILTER_NAME = "official_view";

    @Inject
    private INavigatorRoomsProvider navigatorRoomsProvider;

    @Inject
    private INavigatorManager navigatorManager;

    public List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo) {
        boolean showInvisible = false; // TODO: Implement permissions

        final List<INavigatorResultCategory> categories = new ArrayList<>();
        int order = 0;

        categories.add(new NavigatorResultCategory(
            order,
            "official-root",
            "",
            NavigatorListAction.NONE,
            NavigatorDisplayMode.THUMBNAILS,
            NavigatorLayoutDisplay.DEFAULT,
            this.navigatorRoomsProvider.getRoomFromCategory("official-root", habbo),
            false,
            showInvisible,
            NavigatorDisplayOrder.ORDER_NUMERICAL,
            -1
        ));

        for (final INavigatorPublicCategory publicCategory : this.navigatorManager.getPublicCategories().values()) {
            categories.add(new NavigatorResultCategory(
                    ++order,
                    "",
                    publicCategory.getName(),
                    NavigatorListAction.NONE,
                    NavigatorDisplayMode.LIST, // TODO: Implement habbo display mode's
                    NavigatorLayoutDisplay.DEFAULT, // TODO: Implement habbo layout display's
                    publicCategory.getRooms(),
                    true,
                    showInvisible,
                    NavigatorDisplayOrder.ORDER_NUMERICAL,
                    publicCategory.getOrder()
            ));
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
