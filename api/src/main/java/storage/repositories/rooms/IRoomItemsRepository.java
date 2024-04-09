package storage.repositories.rooms;

import storage.results.IConnectionBooleanResultConsumer;
import storage.results.IConnectionResultConsumer;

public interface IRoomItemsRepository {
    void getAllRoomItems(int roomId, IConnectionResultConsumer consumer);

    void placeFloorItemFromInventory(IConnectionBooleanResultConsumer consumer, int roomId, int id, int x, int y, double z, int rotation);

    void placeWallItemFromInventory(IConnectionBooleanResultConsumer consumer, int roomId, int id, String wallPosition);

    void getTeleportPair(long teleportId, IConnectionResultConsumer consumer);
}
