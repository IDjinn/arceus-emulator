package habbo.navigator.data;

import habbo.navigator.enums.NavigatorFilterComparator;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

public class NavigatorFilterType implements INavigatorFilterType, IFillable {
    public String key;
    public String field;
    public String query;
    public NavigatorFilterComparator comparator;

    public NavigatorFilterType(IConnectionResult result) {
        try {
            this.fill(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getField() {
        return this.field;
    }

    @Override
    public NavigatorFilterComparator getComparator() {
        return this.comparator;
    }

    @Override
    public String getQuery() {
        return this.query;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.key = result.getString("key");
        this.field = result.getString("field");
        this.query = result.getString("database_query");
        this.comparator = NavigatorFilterComparator.valueOf(result.getString("compare").toUpperCase());
    }
}
