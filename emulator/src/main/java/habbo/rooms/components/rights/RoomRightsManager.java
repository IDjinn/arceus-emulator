package habbo.rooms.components.rights;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.RoomRightLevel;
import habbo.rooms.entities.IRoomEntity;

public class RoomRightsManager implements IRoomRightsManager {
    private IRoom room;


    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public void init(IRoom room) {
        this.room = room;
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean hasRights(IHabbo habbo) {
        return true;
    }

    @Override
    public boolean hasRights(IHabbo habbo, RoomRightLevel level) {
        return true;
    }

    @Override
    public RoomRightLevel getRightLevelFor(IHabbo habbo) {
        return RoomRightLevel.Moderator;
    }

    @Override
    public boolean canType(final IRoomEntity entity) {
        return true;
    }

    @Override
    public boolean canTalk(final IRoomEntity entity) {
        return true;
    }
}
