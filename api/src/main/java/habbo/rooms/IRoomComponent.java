package habbo.rooms;

public interface IRoomComponent {
    public IRoom getRoom();

    public void init(IRoom room);

    public void onRoomLoaded();

    public void destroy();
}
