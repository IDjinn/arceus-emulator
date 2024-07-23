package habbo.rooms.entities.status;

import java.util.EnumSet;
import java.util.Set;

public interface RequiresStatus {
    default Set<RoomEntityStatus> getRequirements() {
        return EnumSet.noneOf(RoomEntityStatus.class);
    }
}
