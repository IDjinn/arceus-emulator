package habbo.rooms.entities;

import com.google.inject.Inject;
import core.configuration.IConfigurationManager;
import habbo.habbos.IHabbo;
import habbo.internationalization.IInternationalizationManager;
import habbo.rooms.RoomRightLevel;
import habbo.rooms.entities.components.variables.EntityVariablesManager;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import habbo.rooms.entities.variables.IEntityVariableManager;
import habbo.variables.IVariable;
import networking.client.IClient;
import networking.packets.OutgoingPacket;
import packets.outgoing.rooms.entities.variables.EntityVariablesComposer;

import java.util.Objects;

public class PlayerEntity extends RoomEntity implements IPlayerEntity {
    public static final double PLAYER_HEIGHT = 2d;
    private final IHabbo habbo;
    private final IEntityVariableManager entityVariableManager;

    @Inject
    private IConfigurationManager configurationManager;

    private final IInternationalizationManager internationalizationManager;

    public PlayerEntity(IHabbo habbo, final IInternationalizationManager internationalizationManager) {
        super(habbo.getRoom(), habbo.getData().getId());
        this.habbo = habbo;
        this.internationalizationManager = internationalizationManager;
        this.entityVariableManager = new EntityVariablesManager(this);
        this.setStatus(new StatusBucket(RoomEntityStatus.FLAT_CONTROL, String.valueOf(RoomRightLevel.Moderator.ordinal())));
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
        return Integer.parseInt(Objects.requireNonNull(this.getStatus().get(RoomEntityStatus.FLAT_CONTROL).getValue())) > RoomRightLevel.None.ordinal();
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

        if (!this.configurationManager.getBool("variables.entities.enabled")) {
            packet.appendInt(0);
            return;
        }
        
        final var visibleVariables =
                this.getEntityVariablesManager().getVariables().values().stream().filter(IVariable::isVisible).toList();
        packet.appendInt(visibleVariables.size());
        for (final var variable : visibleVariables) {
            variable.serialize(packet);
        }
    }

    @Override
    public void serializeStatus(OutgoingPacket packet) {

    }

    @Override
    public double getHeight() {
        return PLAYER_HEIGHT;
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
    public boolean equals(final Object obj) {
        if (obj instanceof IPlayerEntity otherPlayer)
            return this.getHabbo().getData().getId() == otherPlayer.getHabbo().getData().getId();
        return false;
    }
}
