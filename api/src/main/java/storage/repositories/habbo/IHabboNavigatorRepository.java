package storage.repositories.habbo;

import storage.results.IConnectionResultConsumer;

public interface IHabboNavigatorRepository {
    void loadNavigatorSearches(IConnectionResultConsumer consumer, int habboId);
}
