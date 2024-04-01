package habbohotel.rooms;

import org.jetbrains.annotations.NotNull;

public class Room implements IRoom {
    private int id;
    private String name;
    private String password;
    private int maxUsers;

    public Room(int roomId, String roomName) {
        this.id = roomId;
        this.name = roomName;
        this.maxUsers = 0;
        this.password = "";
    }


    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public int getMaxUsers() {
        return 0;
    }

    @Override
    public void setMaxUsers(int maxUsers) {

    }

    @Override
    public int getMinUsers() {
        return 0;
    }

    @Override
    public void setMinUsers(int minUsers) {

    }

    @Override
    public boolean isPublic() {
        return false;
    }

    @Override
    public void setPublic(boolean isPublic) {

    }

    @Override
    public RoomAccess getRoomAccess() {
        return null;
    }

    @Override
    public void setRoomAccess(RoomAccess roomAccess) {

    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public int compareTo(@NotNull IRoom o) {
        return o.getId() - id;
    }
}
