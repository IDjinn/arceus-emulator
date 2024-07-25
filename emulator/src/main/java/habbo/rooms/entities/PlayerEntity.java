package habbo.rooms.entities;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.rooms.RoomRightLevel;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import habbo.rooms.entities.variables.IEntityVariablesComponent;
import networking.client.IClient;
import networking.packets.outgoing.IOutgoingDTOSerializer;

import java.util.Objects;

public class PlayerEntity extends RoomEntity implements IPlayerEntity {
    private final IHabbo habbo;
    private @Inject IEntityVariablesComponent entityVariableManager;

    public PlayerEntity(IHabbo habbo) {
        super(habbo.getRoom(), habbo.getData().getId());
        this.habbo = habbo;
        this.getStatusComponent().setStatus(new StatusBucket(RoomEntityStatus.FLAT_CONTROL, String.valueOf(RoomRightLevel.Moderator.ordinal())));
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
    public boolean hasRights() {
        return Integer.parseInt(Objects.requireNonNull(this.getStatusComponent().getStatus().get(RoomEntityStatus.FLAT_CONTROL).getValue())) > RoomRightLevel.None.ordinal();
    }

    @Override
    public void serialize(IOutgoingDTOSerializer<U> packet) {
        packet
                .appendInt(this.getHabbo().getData().getId())
                .appendString(this.getHabbo().getData().getUsername())
                .appendString(this.getHabbo().getData().getMotto())
                .appendString(this.getHabbo().getData().getLook())
                .appendInt(this.getVirtualId())
                .appendInt(this.getPositionComponent().getPosition().getX())
                .appendInt(this.getPositionComponent().getPosition().getY())
                .appendString(String.valueOf(this.getPositionComponent().getPosition().getZ()))
                .appendInt(this.getPositionComponent().getDirection().ordinal())
                .appendInt(1, "habbo type")
                .appendString(this.getHabbo().getData().getGender())
                .appendInt(-1, "group id")
                .appendInt(-1, "group status")
                .appendString("", "guild name")
                .appendString("", "swim figure")
                .appendInt(this.getHabbo().getSettings().getAchievementScore())
                .appendBoolean(true, "is moderator")
                .appendString(this.getHabbo().getSettings().getBanner().orElse(""))
        ;

        this.getEntityVariablesComponent().serialize(packet);
    }


    @Override
    public synchronized void tick() {
        this.getEntityVariablesComponent().tick();
    }

    @Override
    public IEntityVariablesComponent getEntityVariablesComponent() {
        return this.entityVariableManager;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof IPlayerEntity otherPlayer)
            return this.getHabbo().getData().getId() == otherPlayer.getHabbo().getData().getId();
        return false;
    }
}
