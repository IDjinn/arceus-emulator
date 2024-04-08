package storage.repositories.habbo;

import storage.results.IConnectionResultConsumer;

public interface IHabboNavigatorRepository {
    void loadNavigatorSearches(IConnectionResultConsumer consumer, int habboId);

    void loadNavigatorCategoriesSettings(IConnectionResultConsumer consumer, int habboId);
}
