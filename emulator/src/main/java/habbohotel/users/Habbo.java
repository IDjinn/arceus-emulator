package habbohotel.users;

import habbohotel.rooms.IRoom;
import habbohotel.rooms.entities.IPlayerEntity;
import networking.client.INitroClient;
import org.jetbrains.annotations.Nullable;

public class Habbo implements IHabbo {
    private final INitroClient client;
    private final int id;
    private String name;
    private @Nullable IRoom room;
    private @Nullable IPlayerEntity entity;
    private String look = "he-3884-92-93.ch-4004-92.hr-3251-39-49.hd-3100-5.lg-3078-110";
    private String gender = "M";

    public Habbo(INitroClient client, int id, String name) {
        this.client = client;
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMotto() {
        return null;
    }

    @Override
    public void setMotto(String motto) {

    }

    @Override
    public String getLook() {
        return this.look;
    }

    @Override
    public void setLook(String look) {
        assert look != null;
        this.look = look;
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

    @Override
    public void setPlayerEntity(@Nullable IPlayerEntity entity) {
        this.entity = entity;
    }

    @Nullable
    @Override
    public IPlayerEntity getPlayerEntity() {
        return this.entity;
    }

    @Override
    public String getGender() {
        return this.gender;
    }

    @Override
    public boolean isInRoom() {
        return this.room != null;
    }
}


