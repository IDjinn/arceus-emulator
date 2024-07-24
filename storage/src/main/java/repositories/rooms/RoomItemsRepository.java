package repositories.rooms;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import queries.rooms.RoomItemsQuery;
import repositories.ConnectionRepository;
import storage.data.IConnectionStatementConsumer;
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

    @Override
    public void updateItemsBatch(final IConnectionStatementConsumer statementConsumer,
                                 final IConnectionResultConsumer resultConsumer) {
        this.updateBatch(RoomItemsQuery.UPDATE_ITEM.get(), statementConsumer, resultConsumer);
    }

    @Override
    public void updateBatch(final String query, final IConnectionStatementConsumer statementConsumer, final IConnectionResultConsumer resultConsumer) {
        super.updateBatch(query, statementConsumer, resultConsumer);
    }

    @Override
    public void update(final String query, final IConnectionBooleanResultConsumer consumer, final Object... parameters) {
        super.update(query, consumer, parameters);
    }
}
