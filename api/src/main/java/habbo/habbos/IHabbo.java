package habbo.habbos;

import habbo.habbos.data.IHabboData;
import habbo.habbos.data.IHabboNavigator;
import habbo.habbos.data.IHabboRooms;
import habbo.habbos.data.IHabboSettings;
import habbo.habbos.data.badges.IHabboBadgesComponent;
import habbo.habbos.data.wallet.IHabboWallet;
import habbo.habbos.inventory.IHabboInventory;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import networking.client.IClient;

import javax.annotation.Nullable;

public interface IHabbo {

    void init();

    void onLoaded();

    void update(); // TODO

    void destroy();

    IClient getClient();

    void setRoom(@Nullable IRoom room);

    IRoom getRoom();

    IPlayerEntity getPlayerEntity();

    void setPlayerEntity(@Nullable IPlayerEntity entity);

    IHabboData getData();

    IHabboSettings getSettings();

    IHabboInventory getInventory();

    IHabboNavigator getNavigator();

    IHabboRooms getRooms();

    IHabboWallet getWallet();

    IHabboBadgesComponent getBadgesComponent();
}
