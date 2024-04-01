package habbohotel.rooms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@Singleton
public class RoomManager implements IRoomManager {
    private final ConcurrentHashMap<Integer, IRoom> rooms;
    private final IRoomFactory roomFactory;

    @Inject
    public RoomManager(IRoomFactory roomFactory) {
        this.roomFactory = roomFactory;
        rooms = new ConcurrentHashMap<>();

    }

    @Override
    public ConcurrentHashMap<Integer, IRoom> getLoadedRooms() {
        return this.rooms;
    }

    @Override
    public List<IRoom> getLoadedRoomsBy(Predicate<IRoom> predicate) {
        var result = new ArrayList<IRoom>();
        for (var room : rooms.values()) {
            if (predicate.test(room))
                result.add(room);
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public void init() throws InterruptedException {
        var room = roomFactory.createRoom(1, "first-room");
        rooms.put(room.getId(), room);
    }

    @Override
    public void destroy() {
        rooms.values().forEach(IRoom::destroy);
    }
}
