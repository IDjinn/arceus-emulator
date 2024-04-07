package repositories.habbo;

import queries.habbo.HabboInventoryQuery;
import repositories.ConnectionRepository;
import storage.data.IConnectionStatementConsumer;
import storage.repositories.habbo.IHabboInventoryRepository;
import storage.results.IConnectionResultConsumer;

public class HabboInventoryRepository extends ConnectionRepository implements IHabboInventoryRepository {
    @Override
    public void getInventoryByOwnerId(int habboId, IConnectionResultConsumer consumer) {
        this.select(HabboInventoryQuery.GET_ALL_ITEMS_BY_OWNER_ID.get(), consumer, habboId);
    }

    @Override
    public void createInventoryItem(IConnectionStatementConsumer consumer, IConnectionResultConsumer resultConsumer) {
        this.updateBatch(HabboInventoryQuery.INSERT_ITEM.get(), consumer, resultConsumer);
    }
}
