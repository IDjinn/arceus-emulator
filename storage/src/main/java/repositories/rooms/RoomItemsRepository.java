package repositories.rooms;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import queries.rooms.RoomItemsQuery;
import repositories.ConnectionRepository;
import storage.repositories.rooms.IRoomItemsRepository;
import storage.results.IConnectionBooleanResultConsumer;
import storage.results.IConnectionResultConsumer;

@Singleton
public class RoomItemsRepository extends ConnectionRepository implements IRoomItemsRepository {
    @Inject
    IRoomItemFactory roomItemFactory;

    @Override
    public void getAllRoomItems(int roomId, IConnectionResultConsumer consumer) {
        this.select(RoomItemsQuery.SELECT_ALL_ITEMS.get(), consumer, roomId);
    }

    @Override
    public void placeFloorItemFromInventory(IConnectionBooleanResultConsumer consumer, int roomId, int id, int x, int y, double z, int rotation) {
        this.update(RoomItemsQuery.PLACE_FLOOR_ITEM.get(), consumer, roomId, x, y, z, rotation, id);
    }

    @Override
    public void placeWallItemFromInventory(IConnectionBooleanResultConsumer consumer, int roomId, int id, String wallPosition) {
        this.update(RoomItemsQuery.PLACE_WALL_ITEM.get(), consumer, roomId, wallPosition, id);
    }

    @Override
    public void getTeleportPair(final long teleportId, final IConnectionResultConsumer consumer) {
        this.select(RoomItemsQuery.FIND_TELEPORT_PAIR.get(), consumer, teleportId, teleportId);
    }
}
