package storage.repositories.habbo;

import storage.data.IConnectionStatementConsumer;
import storage.results.IConnectionBooleanResultConsumer;
import storage.results.IConnectionResultConsumer;

public interface IHabboInventoryRepository {
    void getInventoryByOwnerId(int habboId, IConnectionResultConsumer consumer);

    void pickupItem(IConnectionBooleanResultConsumer consumer, int itemId, int ownerId);

    void createInventoryItem(IConnectionStatementConsumer consumer, IConnectionResultConsumer resultConsumer);
}
