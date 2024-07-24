package storage.repositories.rooms;

import storage.results.IConnectionResultConsumer;

public interface IRoomRepository {
    void loadPublicRooms(IConnectionResultConsumer consumer, String isPublic, String isStaffPicked);

    void loadStaffPickedRooms(IConnectionResultConsumer consumer, String isStaffPicked);

    void loadFlatCategories(IConnectionResultConsumer consumer);

    void loadRoomModels(IConnectionResultConsumer consumer);
}
