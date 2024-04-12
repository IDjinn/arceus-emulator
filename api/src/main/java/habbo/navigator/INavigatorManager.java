package habbo.navigator;

import core.IHotelService;
import habbo.navigator.data.INavigatorEventCategory;
import habbo.navigator.data.INavigatorFilterType;
import habbo.navigator.data.INavigatorPublicCategory;
import habbo.navigator.tabs.INavigatorTab;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface INavigatorManager extends IHotelService {
    String normalizeTab(@Nullable String view);

    Map<Integer, INavigatorEventCategory> getEventCategories();

    Map<Integer, INavigatorPublicCategory> getPublicCategories();

    INavigatorPublicCategory getPublicCategoryById(int id);

    INavigatorTab getTab(String tabName);

    INavigatorFilterType getFilterTypeByKey(String key);

    INavigatorFilterType getReplaceableFilterTypeByKey(String key, String fallbackKey);
}
