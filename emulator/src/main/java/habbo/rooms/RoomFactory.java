package habbo.rooms;

import com.google.inject.Inject;
import com.google.inject.Injector;
import storage.results.IConnectionResult;
import storage.results.IConnectionResultConsumer;

public class RoomFactory implements IRoomFactory {

    private final Injector injector;

    @Inject
    public RoomFactory(Injector injector) {
        this.injector = injector;
    }

    @Override
    public IRoom createRoom(IConnectionResult data) {
        var room = new Room(data);
        injector.injectMembers(room);
        return room;
    }
}
