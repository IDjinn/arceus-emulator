package repositories.rooms;

import queries.rooms.RoomQuery;
import repositories.ConnectionRepository;
import storage.repositories.rooms.IRoomItemsRepository;
import storage.results.IConnectionResultConsumer;

public class RoomItemsRepository extends ConnectionRepository implements IRoomItemsRepository {
    @Override
    public void getAllRoomItems(int roomId, IConnectionResultConsumer consumer) {
        this.select(RoomQuery.SELECT_ALL_ITEMS.get(), consumer, roomId);
    }
}
