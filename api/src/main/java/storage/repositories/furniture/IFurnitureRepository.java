package storage.repositories.furniture;

import storage.results.IConnectionResultConsumer;

public interface IFurnitureRepository {
    public void getAllFurniture(IConnectionResultConsumer consumer);
}
