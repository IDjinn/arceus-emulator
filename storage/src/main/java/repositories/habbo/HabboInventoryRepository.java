package repositories.habbo;

import com.google.inject.Singleton;
import queries.habbo.HabboQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboInventoryRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class HabboInventoryRepository extends ConnectionRepository implements IHabboInventoryRepository {
    @Override
    public void getInventoryByOwnerId(int habboId, IConnectionResultConsumer consumer) {
        this.select(HabboQuery.GET_ALL_ITEMS_BY_OWNER_ID.get(), consumer, habboId);
    }
}
