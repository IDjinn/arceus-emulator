package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import networking.client.INitroClient;

public interface IHabboEntity extends IRoomEntity {
    IHabbo getHabbo();

    INitroClient getClient();
}
