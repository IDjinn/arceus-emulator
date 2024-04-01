package habbohotel.rooms.entities;

import habbohotel.rooms.IRoom;

public class RoomEntity implements IRoomEntity {
    private final int virutalId;
    private String name;
    private IRoom room;

    public RoomEntity(IRoom room, int virutalId) {
        this.virutalId = virutalId;
        this.room = room;
    }

    @Override
    public int getVirtualId() {
        return this.virutalId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }
}
