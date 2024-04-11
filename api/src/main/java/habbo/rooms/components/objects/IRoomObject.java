package habbo.rooms.components.objects;

import habbo.rooms.IRoom;
import utils.interfaces.IDisposable;

public interface IRoomObject extends IDisposable {
    int getVirtualId();

    IRoom getRoom();

    default void onRoomLoaded() {

    }

    default void init() {

    }

}
