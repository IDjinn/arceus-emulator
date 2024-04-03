package habbo.rooms;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class RoomFactory implements IRoomFactory {

    private final Injector injector;

    @Inject
    public RoomFactory(Injector injector) {
        this.injector = injector;
    }

    @Override
    public IRoom createRoom(int roomId, String roomName) {
        var room = new Room(roomId, roomName);
        injector.injectMembers(room);
        return room;
    }
}
