package storage.repositories.rooms;

import storage.results.IConnectionResultConsumer;

public interface IRoomItemsRepository {
    void getAllRoomItems(int roomId, IConnectionResultConsumer consumer);
}
