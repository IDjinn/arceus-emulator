package repositories.furniture;

import queries.furniture.FunitureQuery;
import repositories.ConnectionRepository;
import storage.repositories.furniture.IFurnitureRepository;
import storage.results.IConnectionResultConsumer;

public class FurnitureRepository extends ConnectionRepository implements IFurnitureRepository {
    @Override
    public void getAllFurniture(IConnectionResultConsumer consumer) {
        this.select(FunitureQuery.SELECT_ALL_FURNITURE.get(), consumer);
    }
}
