package habbo.habbos;

import com.google.inject.Injector;
import habbo.habbos.data.*;
import habbo.habbos.data.wallet.HabboWallet;
import habbo.habbos.data.wallet.IHabboWallet;
import habbo.habbos.inventory.IHabboInventory;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IHabboEntity;
import networking.client.INitroClient;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;

public class Habbo implements IHabbo {
    private final INitroClient client;

    private @Nullable IRoom room;
    private @Nullable IHabboEntity entity;

    private final IHabboData data;

    private final IHabboSettings settings;

    private final IHabboInventory inventory;

    private final IHabboNavigator navigator;

    private final IHabboRooms rooms;
    private final IHabboWallet wallet;

    public Habbo(final Injector injector, INitroClient client, IConnectionResult result) {
        this.client = client;

        this.data = new HabboData(this, result);
        this.settings = new HabboSettings(this, result);
        this.inventory = new HabboInventory(this);
        this.navigator = new HabboNavigator(this, result);
        this.rooms = new HabboRooms(this);
        this.wallet = new HabboWallet(this);

        this.injectDependenciesOnComponents(injector);
    }

    private void injectDependenciesOnComponents(final Injector injector) {
        injector.injectMembers(this.inventory);
        injector.injectMembers(this.navigator);
        injector.injectMembers(this.rooms);
        injector.injectMembers(this.wallet);
    }

    @Override
    public void init() {
        this.data.init();
        this.settings.init();
        this.inventory.init();
        this.navigator.init();
        this.rooms.init();
        this.wallet.init();
    }

    @Override
    public void onLoaded() {

    }

    @Override
    public void update() {
        this.data.update();
        this.settings.update();
        this.inventory.update();
        this.navigator.update();
        this.rooms.update();
        this.wallet.update();
    }

    @Override
    public void destroy() {
        this.data.destory();
        this.settings.destory();
        this.inventory.destory();
        this.navigator.destory();
        this.rooms.destory();
        this.wallet.destory();
    }
    

    public INitroClient getClient() {
        return this.client;
    }

    @Override
    public void setRoom(@Nullable IRoom room) {
        this.room = room;
    }

    @Nullable
    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Nullable
    @Override
    public IHabboEntity getPlayerEntity() {
        return this.entity;
    }

    @Override
    public void setPlayerEntity(@Nullable IHabboEntity entity) {
        this.entity = entity;
    }

    @Override
    public IHabboData getData() {
        return this.data;
    }

    @Override
    public IHabboSettings getSettings() {
        return this.settings;
    }

    @Override
    public IHabboInventory getInventory() {
        return this.inventory;
    }

    @Override
    public IHabboNavigator getNavigator() {
        return this.navigator;
    }

    @Override
    public IHabboRooms getRooms() {
        return this.rooms;
    }

    @Override
    public IHabboWallet getWallet() {
        return this.wallet;
    }
}


