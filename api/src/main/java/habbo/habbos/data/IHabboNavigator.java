package habbo.habbos.data;

import habbo.habbos.data.navigator.IHabboNavigatorCategorySettings;
import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import habbo.habbos.data.navigator.IHabboNavigatorWindowSettings;
import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.navigator.enums.NavigatorLayoutDisplay;

import java.util.List;

public interface IHabboNavigator {
    IHabboNavigatorWindowSettings getNavigatorWindowSettings();

    List<IHabboNavigatorSearch> getNavigatorSearches();

    IHabboNavigatorCategorySettings getNavigatorCategorySettingsFromName(String name);

    NavigatorDisplayMode getDisplayModeForCategory(String name);

    NavigatorDisplayMode getDisplayModeForCategory(String name, NavigatorDisplayMode defaultMode);

    NavigatorLayoutDisplay getLayoutDisplayForCategory(String name);

    NavigatorLayoutDisplay getLayoutDisplayForCategory(String name, NavigatorLayoutDisplay defaultLayout);

    void init();
}
