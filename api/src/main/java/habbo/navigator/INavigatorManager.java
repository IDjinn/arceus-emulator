package habbo.navigator;

import habbo.navigator.data.INavigatorEventCategory;
import habbo.navigator.data.INavigatorPublicCategory;
import habbo.navigator.tabs.INavigatorTab;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public interface INavigatorManager {
    String normalizeTab(@Nullable String view);

    void init();

    HashMap<Integer, INavigatorEventCategory> getEventCategories();

    INavigatorPublicCategory getPublicCategoryById(int id);

    INavigatorTab getTab(String tabName);
}
