package habbo.rooms.entities;

import utils.cycle.ICycle;

public interface IEntityComponent extends ICycle {
    default void init() {

    }

    default void onLoaded() {

    }

    default void destroy() {

    }
}
