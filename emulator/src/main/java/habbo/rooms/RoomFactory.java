package habbo.rooms;

public class RoomFactory implements IRoomFactory {
    @Override
    public IRoom createRoom(int roomId, String roomName) {
        return new Room(roomId, roomName);
    }
}
