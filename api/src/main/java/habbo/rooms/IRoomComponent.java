package habbo.rooms;

public interface IRoomComponent {
    public IRoom getRoom();

    public void init();

    public void onRoomLoaded();

    public void destroy();
}
