package habbohotel.rooms;

import networking.util.ISerializable;
import utils.IDisposable;

public interface IRoom extends Comparable<IRoom>, IDisposable, ISerializable {
    public int getId();

    public String getName();

    public String getPassword();

    public void setPassword(String password);

    public int getMaxUsers();

    public void setMaxUsers(int maxUsers);

    public int getMinUsers();

    public void setMinUsers(int minUsers);

    public boolean isPublic();

    public void setPublic(boolean isPublic);

    public RoomAccess getRoomAccess();

    public void setRoomAccess(RoomAccess roomAccess);


    public void init();

    public void destroy();
}
