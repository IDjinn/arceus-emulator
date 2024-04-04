package repositories.habbo;

import queries.habbo.HabboQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboInventoryRepository;
import storage.results.IConnectionResultConsumer;

public class HabboInventoryRepository extends ConnectionRepository implements IHabboInventoryRepository {
    @Override
    public void getInventoryByOwnerId(int habboId, IConnectionResultConsumer consumer) {
        this.select(HabboQuery.GET_ALL_ITEMS_BY_OWNER_ID.get(), consumer, habboId);
    }
}
