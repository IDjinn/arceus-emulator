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
import habbo.navigator.enums.NavigatorListAction;
import habbo.rooms.data.IRoomCategory;

import java.util.ArrayList;
import java.util.List;

public class NavigatorOfficialTab implements INavigatorTab {
    public final static String FILTER_NAME = "official_view";

    private final String category = "official-root";

    @Inject
    private INavigatorRoomsProvider navigatorRoomsProvider;

    @Inject
    private INavigatorManager navigatorManager;

    public List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo) {
        boolean showInvisible = false; // TODO: Implement permissions

        int order = 0;
        final List<INavigatorResultCategory> categories = new ArrayList<>();

        categories.add(new NavigatorResultCategory(
            order,
            this.category,
            "",
            NavigatorListAction.NONE,
            habbo.getNavigator().getDisplayModeForCategory(this.category, NavigatorDisplayMode.THUMBNAILS),
            habbo.getNavigator().getLayoutDisplayForCategory(this.category),
            this.navigatorRoomsProvider.getRoomsForCategory(this.category, habbo),
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
                    habbo.getNavigator().getDisplayModeForCategory(publicCategory.getName(), publicCategory.getDisplayMode()),
                    habbo.getNavigator().getLayoutDisplayForCategory(publicCategory.getName()),
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
        return this.getResultForHabbo(habbo).stream()
                .filter(result -> result.filterRooms(filterType, search))
                .toList();
    }
}
