package habbo.navigator.tabs;

import habbo.habbos.IHabbo;
import habbo.navigator.data.INavigatorFilterType;
import habbo.navigator.data.INavigatorResultCategory;
import habbo.rooms.data.IRoomCategory;

import java.util.List;

public interface INavigatorTab {
    List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo);

    List<INavigatorResultCategory> getSearchedResultForHabbo(IHabbo habbo, INavigatorFilterType filterType, String search, IRoomCategory category);
}
