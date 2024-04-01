package habbohotel.users;

import habbohotel.rooms.IRoom;
import networking.client.INitroClient;
import org.jetbrains.annotations.Nullable;

public class Habbo implements IHabbo {
    private final INitroClient client;
    private final int id;
    private String name;
    private @Nullable IRoom room;

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
}
