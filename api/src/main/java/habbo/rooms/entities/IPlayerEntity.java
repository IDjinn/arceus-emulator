package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import networking.client.IClient;

public interface IPlayerEntity extends IRoomEntity {
    IHabbo getHabbo();

    IClient getClient();

    boolean hasRights();
}
