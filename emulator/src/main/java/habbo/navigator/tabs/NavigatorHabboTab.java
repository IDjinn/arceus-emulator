package habbo.navigator.tabs;

import habbo.habbos.IHabbo;
import habbo.navigator.data.INavigatorFilterType;
import habbo.navigator.data.INavigatorResultCategory;
import habbo.rooms.data.IRoomCategory;

import java.util.List;

public class NavigatorHabboTab implements INavigatorTab {
    public final static String FILTER_NAME = "myworld_view";

    public List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo) {
        return null;
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
