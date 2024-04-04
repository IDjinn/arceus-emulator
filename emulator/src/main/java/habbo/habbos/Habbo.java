package habbo.habbos;

import habbo.habbos.data.HabboData;
import habbo.habbos.data.HabboSettings;
import habbo.habbos.data.IHabboData;
import habbo.habbos.data.IHabboSettings;
import habbo.habbos.inventory.HabboInventoryComponent;
import habbo.habbos.inventory.IHabboInventoryComponent;
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
    private final IHabboInventoryComponent inventoryComponent;

    public Habbo(INitroClient client, IConnectionResult result) {
        this.client = client;

        this.data = new HabboData(this, result);
        this.settings = new HabboSettings(this, result);
        this.inventoryComponent = new HabboInventoryComponent(this, result);
    }

    @Override
    public void init() {
        this.data.init();
        this.settings.init();
        this.inventoryComponent.init();
    }

    @Override
    public void onLoaded() {

    }

    @Override
    public void destroy() {
        this.data.destory();
        this.settings.destory();
        this.inventoryComponent.destory();
    }

    @Override
    public void update() {
        this.data.update();
        this.settings.update();
        this.inventoryComponent.update();
    }
    

    public INitroClient getClient() {
        return client;
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
    public IHabboInventoryComponent getInventoryComponent() {
        return this.inventoryComponent;
    }
}


