package habbo.rooms.components.objects;

import habbo.rooms.IRoom;

public interface IRoomObject {
    public long getVirtualId();

    public IRoom getRoom();

    public void onRoomLoaded();

    public void init();

    public void destroy();
}
