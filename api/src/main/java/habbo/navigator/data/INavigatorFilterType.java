package habbo.navigator.data;

import habbo.navigator.enums.NavigatorFilterComparator;

public interface INavigatorFilterType {
    String getKey();
    String getField();
    NavigatorFilterComparator getComparator();
    String getQuery();
}
