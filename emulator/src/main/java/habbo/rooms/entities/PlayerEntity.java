package habbo.rooms.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.components.gamemap.ITileMetadata;
import habbo.rooms.entities.components.variables.EntityVariablesManager;
import habbo.rooms.entities.variables.IEntityVariableManager;
import networking.client.IClient;
import networking.packets.IOutgoingPacket;
import packets.outgoing.rooms.entities.variables.EntityVariablesComposer;

public class PlayerEntity extends RoomEntity implements IPlayerEntity {
    public static final double PLAYER_HEIGHT = 2d;
    private final IHabbo habbo;
    private final IEntityVariableManager entityVariableManager;

    public PlayerEntity(IHabbo habbo) {
        super(habbo.getRoom(), habbo.getData().getId());
        this.habbo = habbo;
        this.entityVariableManager = new EntityVariablesManager(this);
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
    public void serialize(IOutgoingPacket packet) {
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
    public double getHeight() {
        return PLAYER_HEIGHT;
    }

    @Override
    public boolean canSlideTo(final ITileMetadata metadata) {
        return metadata.getEntityHeight().isPresent();
    }

    @Override
    public synchronized void tick() {
        super.tick();

        this.handleVariables();
    }


    private void handleVariables() {
        this.getEntityVariablesManager().tick();
        if (!this.getEntityVariablesManager().isNeedUpdate()) return;

        this.getClient().sendMessage(new EntityVariablesComposer(this.getEntityVariablesManager().getVariables()));
        this.getEntityVariablesManager().setNeedUpdate(false);
    }


    @Override
    public IEntityVariableManager getEntityVariablesManager() {
        return this.entityVariableManager;
    }

    @Override
    public boolean canWalk() {
        return false;
    }

    @Override
    public void setCanWalk(final boolean canWalk) {

    }
}
