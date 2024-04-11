package storage.repositories.furniture;

import storage.results.IConnectionResultConsumer;

public interface IFurnitureRepository {
    void getAllFurniture(IConnectionResultConsumer consumer);

    void createTeleportLink(final IConnectionResultConsumer consumer, final int first, final int second);
}
