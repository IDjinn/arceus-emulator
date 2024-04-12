package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.components.gamemap.ITileMetadata;
import networking.client.IClient;
import networking.packets.OutgoingPacket;

public class PlayerEntity extends RoomEntity implements IPlayerEntity {
    public static final double PLAYER_HEIGHT = 2d;
    private final IHabbo habbo;

    public PlayerEntity(IHabbo habbo) {
        super(habbo.getRoom(), habbo.getData().getId());
        this.habbo = habbo;
    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public IClient getClient() {
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
                .appendInt(this.getHabbo().getSettings().getAchievementScore())
                .appendBoolean(true);
    }

    @Override
    public void serializeStatus(OutgoingPacket packet) {

    }

    @Override
    public double getHeight() {
        return PLAYER_HEIGHT;
    }

    @Override
    public boolean canSlideTo(final ITileMetadata metadata) {
        return metadata.getEntityHeight().isPresent();
    }
}
