package habbo.rooms.components.rights;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoomComponent;
import habbo.rooms.RoomRightLevel;

public interface IRoomRightsManager extends IRoomComponent {
    boolean hasRights(IHabbo habbo);

    boolean hasRights(IHabbo habbo, RoomRightLevel level);

    RoomRightLevel getRightLevelFor(IHabbo habbo);
}
