package habbo.rooms;

public interface IRoomComponent {
    IRoom getRoom();

    void init(IRoom room);

    default void update() {

    }

    default void onRoomLoaded() {

    }

    default void destroy() {

    }
}
