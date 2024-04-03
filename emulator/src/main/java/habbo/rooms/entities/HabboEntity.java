package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import networking.client.INitroClient;
import networking.packets.OutgoingPacket;

public class HabboEntity extends RoomEntity implements IHabboEntity {

    private final IHabbo habbo;

    public HabboEntity(IHabbo habbo) { // TODO: VIRTUAL ID
        super(habbo.getRoom(), habbo.getId());
        this.habbo = habbo;
    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public INitroClient getClient() {
        return this.getHabbo().getClient();
    }

    @Override
    public void serialize(OutgoingPacket packet) {
        packet
                .appendInt(getHabbo().getId())
                .appendString(getHabbo().getName())
                .appendString(getHabbo().getMotto())
                .appendString(getHabbo().getLook())
                .appendInt(getVirtualId())
                .appendInt(getPosition().getX())
                .appendInt(getPosition().getY())
                .appendString(String.valueOf(getPosition().getZ()))
                .appendInt(getDirection().ordinal())
                .appendInt(1) // 1 == habbo type
                .appendString("M") // TODO HARD-CODED
                .appendInt(-1)
                .appendInt(-1)
                .appendString("") // guild name
                .appendString("")
                .appendInt(10000) //achievement score
                .appendBoolean(true);
    }

    @Override
    public void serializeStatus(OutgoingPacket packet) {

    }
}
