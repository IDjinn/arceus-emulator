package habbo.habbos.data;

import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import habbo.habbos.data.navigator.IHabboNavigatorWindowSettings;

import java.util.List;

public interface IHabboNavigator {
    IHabboNavigatorWindowSettings getNavigatorWindowSettings();

    List<IHabboNavigatorSearch> getNavigatorSearches();

    void init();
}
