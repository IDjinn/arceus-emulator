package habbohotel.rooms.components.entities;

import habbohotel.rooms.IRoom;

public class RoomEntitiesComponent implements IRoomEntitiesComponent {
    private final IRoom room;

    public RoomEntitiesComponent(IRoom room) {
        this.room = room;
    }

    @Override
    public IRoom getRoom() {
        return room;
    }

    @Override
    public void init() {

    }

    @Override
    public void onRoomLoaded() {

    }

    @Override
    public void destroy() {

    }
}
