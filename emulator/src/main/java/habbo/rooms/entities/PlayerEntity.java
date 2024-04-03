package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import networking.client.INitroClient;
import networking.packets.OutgoingPacket;

public class PlayerEntity extends RoomEntity implements IPlayerEntity {
    static int VIRTUAL_ID = 0;

    private final IHabbo habbo;

    private IRoom room;

    public PlayerEntity(IHabbo habbo, IRoom room) {
        super(room, ++VIRTUAL_ID);

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
                .appendInt(this.getHabbo().getData().getId())
                .appendString(this.getHabbo().getData().getUsername())
                .appendString(this.getHabbo().getData().getMotto())
                .appendString(this.getHabbo().getData().getLook())
                .appendInt(this.getVirtualId())
                .appendInt(this.getPosition().getX())
                .appendInt(this.getPosition().getY())
                .appendString(String.valueOf(this.getPosition().getZ()))
                .appendInt(this.getDirection().ordinal())
                .appendInt(1) // 1 == habbo type
                .appendString("M")
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
