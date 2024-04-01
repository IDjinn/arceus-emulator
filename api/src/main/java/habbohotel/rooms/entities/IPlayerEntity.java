package habbohotel.rooms.entities;

import habbohotel.users.IHabbo;
import networking.client.INitroClient;

public interface IPlayerEntity extends IRoomEntity {
    public IHabbo getHabbo();

    public INitroClient getClient();
}
