package habbo.rooms;

import com.google.inject.Inject;
import com.google.inject.Injector;
import storage.results.IConnectionResult;

public class RoomFactory implements IRoomFactory {

    private final Injector injector;

    private final IRoomManager roomManager;

    @Inject
    public RoomFactory(Injector injector, IRoomManager roomManager) {
        this.injector = injector;
        this.roomManager = roomManager;
    }

    @Override
    public IRoom createRoom(IConnectionResult data) {
        final var room = new Room(data);
        this.injector.injectMembers(room);
        this.roomManager.addRoom(room);
        return room;
    }
}
