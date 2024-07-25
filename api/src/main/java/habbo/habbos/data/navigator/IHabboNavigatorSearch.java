package habbo.habbos.data.navigator;

import utils.interfaces.IFillable;

public interface IHabboNavigatorSearch extends IFillable {
    String getSearchCode();

    String getFilter();

    int getId();
}
