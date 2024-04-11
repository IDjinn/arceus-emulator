package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import networking.client.IClient;

public interface IHabboEntity extends IRoomEntity {
    IHabbo getHabbo();

    IClient getClient();
}
