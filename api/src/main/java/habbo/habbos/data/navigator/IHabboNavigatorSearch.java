package habbo.habbos.data.navigator;

import utils.interfaces.IFillable;
import utils.interfaces.IWriteable;

public interface IHabboNavigatorSearch extends IWriteable, IFillable {
    String getSearchCode();

    String getFilter();

    int getId();
}
