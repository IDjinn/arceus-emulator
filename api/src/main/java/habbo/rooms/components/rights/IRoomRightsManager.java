package habbo.rooms.components.rights;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoomComponent;
import habbo.rooms.RoomRightLevel;

public interface IRoomRightsManager extends IRoomComponent {
    public boolean hasRights(IHabbo habbo);

    public boolean hasRights(IHabbo habbo, RoomRightLevel level);

    public RoomRightLevel getRightLevelFor(IHabbo habbo);
}
