package habbohotel.users;

import habbohotel.rooms.IRoom;
import networking.client.INitroClient;

import javax.annotation.Nullable;

public interface IHabbo {
    public int getId();

    public String getName();

    public void setName(String username);

    public String getMotto();

    public void setMotto(String motto);

    INitroClient getClient();

    void setRoom(@Nullable IRoom room);
}
