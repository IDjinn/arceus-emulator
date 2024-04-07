package habbo.navigator.tabs;

import habbo.habbos.IHabbo;
import habbo.navigator.data.INavigatorResultCategory;

import java.util.List;

public interface INavigatorTab {
    List<INavigatorResultCategory> getResultForHabbo(IHabbo habbo);
}
