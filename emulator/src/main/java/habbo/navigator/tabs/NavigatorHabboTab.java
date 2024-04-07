package habbo.navigator.tabs;

import habbo.habbos.IHabbo;
import habbo.navigator.data.INavigatorResultCategory;

import java.util.List;

public class NavigatorHabboTab implements INavigatorTab {
    public final static String FILTER_NAME = "myworld_view";

    public List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo) {
        return null;
    }
}
