package habbohotel.rooms.components.entities;

import habbohotel.rooms.IRoom;
import habbohotel.rooms.entities.IPlayerEntity;
import habbohotel.rooms.entities.PlayerEntity;
import habbohotel.users.IHabbo;

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

    @Override
    public IPlayerEntity createHabboEntity(IHabbo habbo) {
        return new PlayerEntity(habbo);
    }
}
