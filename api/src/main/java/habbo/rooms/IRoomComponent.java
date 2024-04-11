package habbo.rooms;

public interface IRoomComponent {
    IRoom getRoom();

    void init(IRoom room);

    void onRoomLoaded();

    void destroy();
}
