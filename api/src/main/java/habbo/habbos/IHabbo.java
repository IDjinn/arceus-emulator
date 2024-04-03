package habbo.habbos;

import habbo.habbos.data.IHabboData;
import habbo.habbos.data.IHabboSettings;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IHabboEntity;
import networking.client.INitroClient;

import javax.annotation.Nullable;

public interface IHabbo {
    INitroClient getClient();

    void setRoom(@Nullable IRoom room);

    @Nullable
    IRoom getRoom();

    @Nullable
    IHabboEntity getPlayerEntity();

    void setPlayerEntity(@Nullable IHabboEntity entity);

    IHabboData getData();

    IHabboSettings getSettings();
}
