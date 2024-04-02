package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import networking.client.INitroClient;

public interface IPlayerEntity extends IRoomEntity {
    public IHabbo getHabbo();

    public INitroClient getClient();
}
