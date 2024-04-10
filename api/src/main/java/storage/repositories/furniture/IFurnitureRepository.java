package storage.repositories.furniture;

import storage.results.IConnectionResultConsumer;

public interface IFurnitureRepository {
    public void getAllFurniture(IConnectionResultConsumer consumer);

    public void createTeleportLink(final IConnectionResultConsumer consumer, final int first, final int second);
}
