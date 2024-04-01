package habbohotel.rooms;

public interface IRoomFactory {
    public IRoom createRoom(int roomId, String roomName);
}
