package habbo.habbos;

import habbo.habbos.data.IHabboData;
import habbo.habbos.data.IHabboSettings;
import habbo.rooms.entities.IPlayerEntity;
import networking.client.INitroClient;

import javax.annotation.Nullable;

public interface IHabbo {
    INitroClient getClient();

    void setPlayerEntity(@Nullable IPlayerEntity entity);

    @Nullable
    IPlayerEntity getPlayerEntity();

    IHabboData getData();

    IHabboSettings getSettings();
}
