package repositories.rooms;

import com.google.inject.Singleton;
import queries.rooms.RoomQuery;
import repositories.ConnectionRepository;
import storage.repositories.rooms.IRoomRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class RoomRepository extends ConnectionRepository implements IRoomRepository {
    public void loadPublicRooms(IConnectionResultConsumer consumer, String isPublic, String isStaffPicked) {
        this.select(RoomQuery.SELECT_ALL_PUBLIC_ROOMS.get(), consumer, isPublic, isStaffPicked);
    }

    public void loadStaffPickedRooms(IConnectionResultConsumer consumer, String isStaffPicked) {
        this.select(RoomQuery.SELECT_ALL_STAFF_PICKED_ROOMS.get(), consumer, isStaffPicked);
    }

    public void loadFlatCategories(IConnectionResultConsumer consumer) {
        this.select(RoomQuery.SELECT_ALL_ROOMS_CATEGORIES.get(), consumer);
    }

    public void loadRoomModels(IConnectionResultConsumer consumer) {
        this.select(RoomQuery.SELECT_ALL_ROOM_MODELS.get(), consumer);
    }
}
