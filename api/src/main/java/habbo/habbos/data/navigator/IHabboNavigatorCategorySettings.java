package habbo.habbos.data.navigator;

import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.navigator.enums.NavigatorLayoutDisplay;

public interface IHabboNavigatorCategorySettings {
    String getCaption();
    NavigatorDisplayMode getDisplayMode();
    NavigatorLayoutDisplay getLayoutDisplay();
}
