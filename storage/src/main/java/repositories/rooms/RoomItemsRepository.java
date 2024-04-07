package repositories.rooms;

import com.google.inject.Singleton;
import queries.rooms.RoomQuery;
import repositories.ConnectionRepository;
import storage.repositories.rooms.IRoomItemsRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class RoomItemsRepository extends ConnectionRepository implements IRoomItemsRepository {
    @Override
    public void getAllRoomItems(int roomId, IConnectionResultConsumer consumer) {
        this.select(RoomQuery.SELECT_ALL_ITEMS.get(), consumer, roomId);
    }
}
