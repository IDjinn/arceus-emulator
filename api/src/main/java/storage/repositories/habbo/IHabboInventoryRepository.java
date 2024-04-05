package storage.repositories.habbo;

import storage.results.IConnectionResultConsumer;

public interface IHabboInventoryRepository {
    void getInventoryByOwnerId(int habboId, IConnectionResultConsumer consumer);
}
