package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import networking.client.INitroClient;

public interface IHabboEntity extends IRoomEntity {
    public IHabbo getHabbo();

    public INitroClient getClient();
}
