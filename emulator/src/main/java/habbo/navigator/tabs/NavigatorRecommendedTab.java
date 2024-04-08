package habbo.navigator.tabs;

import com.google.inject.Inject;
import core.configuration.IEmulatorSettings;
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
import habbo.rooms.IRoom;
import habbo.rooms.data.IRoomCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigatorRecommendedTab implements INavigatorTab {
    public final static String FILTER_NAME = "hotel_view";

    private final String category = "popular";

    @Inject
    private INavigatorRoomsProvider navigatorRoomsProvider;

    @Inject
    private INavigatorManager navigatorManager;

    @Inject
    private IEmulatorSettings settings;

    public List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo) {
        boolean showInvisible = false; // TODO: Implement permissions

        int order = 0;
        final List<INavigatorResultCategory> categories = new ArrayList<>();

        categories.add(new NavigatorResultCategory(
                order,
                this.category,
                "",
                NavigatorListAction.NONE,
                habbo.getNavigator().getDisplayModeForCategory(this.category, this.getDefaultDisplayMode()),
                habbo.getNavigator().getLayoutDisplayForCategory(this.category),
                this.navigatorRoomsProvider.getRoomFromCategory(this.category, habbo),
                false,
                showInvisible,
                NavigatorDisplayOrder.ORDER_NUMERICAL,
                -1
        ));

        final HashMap<IRoomCategory, List<IRoom>> roomsByCategory = this.navigatorRoomsProvider.getRoomsFromCategories(habbo);

        for (final Map.Entry<IRoomCategory, List<IRoom>> entry : roomsByCategory.entrySet()) {
            categories.add(new NavigatorResultCategory(
                    ++order,
                    entry.getKey().getCaption(),
                    entry.getKey().getCaption(),
                    NavigatorListAction.MORE,
                    habbo.getNavigator().getDisplayModeForCategory(entry.getKey().getCaptionSave()),
                    habbo.getNavigator().getLayoutDisplayForCategory(entry.getKey().getCaptionSave()),
                    entry.getValue(),
                    true,
                    showInvisible,
                    NavigatorDisplayOrder.ORDER_NUMERICAL,
                    entry.getKey().getOrder()
            ));
        }

        return categories;
    }

    private NavigatorDisplayMode getDefaultDisplayMode() {
        final String displayMode = this.settings.getOrDefault("hotel.navigator.popular.listtype", "0");

        try {
            return NavigatorDisplayMode.fromType(Integer.parseInt(displayMode));
        } catch (NumberFormatException e) {
            return NavigatorDisplayMode.LIST;
        }
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
