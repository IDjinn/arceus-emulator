package habbo.habbos;

import habbo.habbos.data.HabboData;
import habbo.habbos.data.HabboSettings;
import habbo.habbos.data.IHabboData;
import habbo.habbos.data.IHabboSettings;
import habbo.rooms.entities.IHabboEntity;
import habbo.rooms.entities.IPlayerEntity;
import networking.client.INitroClient;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;

public class Habbo implements IHabbo {
    private final INitroClient client;

    private @Nullable IRoom room;
    private @Nullable IHabboEntity entity;

    private final IHabboData data;

    private final IHabboSettings settings;

    public Habbo(INitroClient client, IConnectionResult result) {
        this.client = client;

        this.data = new HabboData(result);
        this.settings = new HabboSettings(result);
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
}


