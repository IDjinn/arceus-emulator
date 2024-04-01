package habbohotel.rooms.entities;

import habbohotel.users.IHabbo;
import networking.client.INitroClient;

public class PlayerEntity extends RoomEntity implements IPlayerEntity {

    private IHabbo habbo;

    public PlayerEntity(IHabbo habbo) { // TODO: VIRTUAL ID
        super(habbo.getRoom(), habbo.getId());
    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public INitroClient getClient() {
        return this.getHabbo().getClient();
    }
}
