package repositories.furniture;

import com.google.inject.Singleton;
import queries.furniture.FunitureQuery;
import repositories.ConnectionRepository;
import storage.repositories.furniture.IFurnitureRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class FurnitureRepository extends ConnectionRepository implements IFurnitureRepository {
    @Override
    public void getAllFurniture(IConnectionResultConsumer consumer) {
        this.select(FunitureQuery.SELECT_ALL_FURNITURE.get(), consumer);
    }

    @Override
    public void createTeleportLink(final IConnectionResultConsumer consumer, final int first, final int second) {
        this.insert(FunitureQuery.CREATE_TELEPORT_LINK.get(), consumer, first, second);
    }
}
