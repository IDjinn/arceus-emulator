package storage.repositories.habbo;

import storage.data.IConnectionStatementConsumer;
import storage.results.IConnectionResultConsumer;

public interface IHabboInventoryRepository {
    void getInventoryByOwnerId(int habboId, IConnectionResultConsumer consumer);

    public void createInventoryItem(IConnectionStatementConsumer consumer, IConnectionResultConsumer resultConsumer);
}
