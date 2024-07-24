package habbo.rooms.entities;

import org.jetbrains.annotations.NotNull;
import utils.cycle.ICycle;

public interface IEntityComponent extends ICycle {
    default void init(IRoomEntity entity) {

    }

    default void onLoaded() {

    }

    default void destroy() {

    }

    @NotNull
    IRoomEntity getEntity();
}
