package storage.repositories.navigator;

import storage.results.IConnectionResultConsumer;

public interface INavigatorRepository {
    void loadPublicCategories(IConnectionResultConsumer consumer);

    void loadFilterTypes(IConnectionResultConsumer consumer);
}
