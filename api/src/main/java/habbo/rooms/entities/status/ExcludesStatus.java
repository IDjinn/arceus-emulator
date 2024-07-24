package habbo.rooms.entities.status;

import java.util.Collections;
import java.util.Set;

public interface ExcludesStatus {
    default Set<RoomEntityStatus> getExcludes() {
        return Collections.emptySet();
    }
}
