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
        var room = new Room(data);
        injector.injectMembers(room);
        room.init();
        room.onLoaded();
        this.roomManager.addRoom(room);
        return room;
    }
}
