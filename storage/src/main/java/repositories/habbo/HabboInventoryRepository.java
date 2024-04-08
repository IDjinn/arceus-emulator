package repositories.habbo;

import com.google.inject.Singleton;
import queries.habbo.HabboQuery;
import queries.habbo.HabboInventoryQuery;
import repositories.ConnectionRepository;
import storage.data.IConnectionStatementConsumer;
import storage.repositories.habbo.IHabboInventoryRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
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
