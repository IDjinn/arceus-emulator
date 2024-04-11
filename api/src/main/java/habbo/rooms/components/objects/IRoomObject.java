package habbo.rooms.components.objects;

import habbo.rooms.IRoom;
import utils.interfaces.IDisposable;

public interface IRoomObject extends IDisposable {
    public int getVirtualId();

    public IRoom getRoom();

    public default void onRoomLoaded() {

    }

    public default void init() {

    }

}
