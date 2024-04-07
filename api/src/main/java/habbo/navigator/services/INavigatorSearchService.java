package habbo.navigator.services;

import habbo.habbos.IHabbo;

public interface INavigatorSearchService {
    void commit(IHabbo habbo, String tabName, String query);
}
