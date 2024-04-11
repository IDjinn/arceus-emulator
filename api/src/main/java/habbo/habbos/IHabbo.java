package habbo.habbos;

import habbo.habbos.data.IHabboData;
import habbo.habbos.data.IHabboNavigator;
import habbo.habbos.data.IHabboRooms;
import habbo.habbos.data.IHabboSettings;
import habbo.habbos.data.wallet.IHabboWallet;
import habbo.habbos.inventory.IHabboInventory;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IHabboEntity;
import networking.client.INitroClient;

import javax.annotation.Nullable;

public interface IHabbo {

    void init();

    void onLoaded();

    void update(); // TODO

    void destroy();

    INitroClient getClient();

    void setRoom(@Nullable IRoom room);

    @Nullable
    IRoom getRoom();

    @Nullable
    IHabboEntity getPlayerEntity();

    void setPlayerEntity(@Nullable IHabboEntity entity);

    IHabboData getData();

    IHabboSettings getSettings();

    IHabboInventory getInventory();

    IHabboNavigator getNavigator();

    IHabboRooms getRooms();

    IHabboWallet getWallet();
}
