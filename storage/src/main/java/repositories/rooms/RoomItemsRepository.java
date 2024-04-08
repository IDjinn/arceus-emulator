package repositories.rooms;

import com.google.inject.Inject;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import queries.rooms.RoomQuery;
import repositories.ConnectionRepository;
import storage.repositories.rooms.IRoomItemsRepository;
import storage.results.IConnectionBooleanResultConsumer;
import storage.results.IConnectionResultConsumer;

public class RoomItemsRepository extends ConnectionRepository implements IRoomItemsRepository {
    @Inject
    IRoomItemFactory roomItemFactory;
    @Override
    public void getAllRoomItems(int roomId, IConnectionResultConsumer consumer) {
        this.select(RoomQuery.SELECT_ALL_ITEMS.get(), consumer, roomId);
    }

    @Override
    public void placeFloorItemFromInventory(IConnectionBooleanResultConsumer consumer, int roomId, int id, int x, int y, double z, int rotation) {
        this.update(RoomQuery.PLACE_FLOOR_ITEM.get(), consumer, roomId, x, y, z, rotation, id);
    }
}
