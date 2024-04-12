package habbo.rooms.components.rights;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoomComponent;
import habbo.rooms.RoomRightLevel;
import habbo.rooms.entities.IRoomEntity;

public interface IRoomRightsManager extends IRoomComponent {
    boolean hasRights(IHabbo habbo);

    boolean hasRights(IHabbo habbo, RoomRightLevel level);

    RoomRightLevel getRightLevelFor(IHabbo habbo);

    boolean canType(IRoomEntity entity);

    boolean canTalk(IRoomEntity entity);
}
