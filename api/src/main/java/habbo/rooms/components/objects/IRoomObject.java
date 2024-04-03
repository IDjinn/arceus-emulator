package habbo.rooms.components.objects;

import habbo.rooms.IRoom;

public interface IRoomObject {
    public int getVirtualId();

    public IRoom getRoom();

    public void onRoomLoaded();

    public void init();

    public void destroy();
}
