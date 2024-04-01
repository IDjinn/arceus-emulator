package habbohotel.rooms.entities;

import habbohotel.rooms.IRoom;

public interface IRoomEntity {
    public int getVirtualId();

    public String getName();

    public void setName(String name);

    public IRoom getRoom();
}
