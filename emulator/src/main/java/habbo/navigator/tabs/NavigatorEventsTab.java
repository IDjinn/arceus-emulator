package habbo.navigator.tabs;

import habbo.habbos.IHabbo;
import habbo.navigator.data.INavigatorFilterType;
import habbo.navigator.data.INavigatorResultCategory;
import habbo.rooms.data.IRoomCategory;

import java.util.ArrayList;
import java.util.List;

public class NavigatorEventsTab implements INavigatorTab {
    public final static String FILTER_NAME = "roomads_view";

    public List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo) {
        // TODO: To make this class work, you need to implement the room promotion system
        return new ArrayList<>();
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
