package habbo.habbos;

import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import networking.client.INitroClient;

import javax.annotation.Nullable;

public interface IHabbo {
    public int getId();

    public String getName();

    public void setName(String username);

    public String getMotto();

    public void setMotto(String motto);

    public String getLook();

    public void setLook(String look);
    
    INitroClient getClient();

    void setRoom(@Nullable IRoom room);

    @Nullable
    IRoom getRoom();

    void setPlayerEntity(@Nullable IPlayerEntity entity);

    @Nullable
    IPlayerEntity getPlayerEntity();

    String getGender();

    boolean isInRoom();
}
