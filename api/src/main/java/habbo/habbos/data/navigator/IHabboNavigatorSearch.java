package habbo.habbos.data.navigator;

import utils.IFillable;
import utils.IWriteable;

public interface IHabboNavigatorSearch extends IWriteable, IFillable {
    String getSearchCode();

    String getFilter();

    int getId();
}
