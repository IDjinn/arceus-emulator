package habbo.commands.rooms.building;

import habbo.commands.ICommand;
import habbo.commands.ICommandContext;
import habbo.internationalization.LocalizedString;
import habbo.rooms.RoomRightLevel;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import habbo.variables.Variable;
import org.jetbrains.annotations.NotNull;
import packets.outgoing.rooms.session.RoomRightsComposer;

import java.util.Optional;

public class BuilderCommand implements ICommand {
    private static final LocalizedString name = LocalizedString.of("command.rooms.building.builder.name");
    private static final LocalizedString description = LocalizedString.of("command.rooms.building.builder.description");
    private static final LocalizedString[] alias = new LocalizedString[]{};

    @Override
    public @NotNull LocalizedString getName() {
        return name;
    }

    @Override
    public @NotNull LocalizedString[] getAlias() {
        return new LocalizedString[0];
    }

    @Override
    public @NotNull LocalizedString getDescription() {
        return description;
    }

    @Override
    public Optional<ICommandContext> validate(final ICommandContext ctx) {
        if (!ctx.getRoom().getRightsManager().hasRights(ctx.getHabbo()))
            return ctx.error(LocalizedString.of("c"));

        return Optional.of(ctx);
    }

    @Override
    public Optional<ICommandContext> execute(final ICommandContext ctx) {
        final var builderVariable = ctx.getPlayerEntity().getEntityVariablesComponent().getOrCreate(new Variable<>(
                "user.command.builder.enabled",
                false
        ));

        builderVariable.setValue(!builderVariable.getValue());
        if (builderVariable.getValue()) {
            final var rightLevel = ctx.getRoom().getRightsManager().getRightLevelFor(ctx.getHabbo());
            ctx.getPlayerEntity().getStatusComponent().setStatus(new StatusBucket(RoomEntityStatus.FLAT_CONTROL, String.valueOf(rightLevel.ordinal())));
            ctx.getClient().sendMessage(new RoomRightsComposer(rightLevel));
        } else {
            ctx.getClient().sendMessage(new RoomRightsComposer(RoomRightLevel.None));
            ctx.getPlayerEntity().getStatusComponent().setStatus(new StatusBucket(RoomEntityStatus.FLAT_CONTROL, String.valueOf(RoomRightLevel.None.ordinal())));
        }

        ctx.whisper(LocalizedString.of("command.rooms.building.builder.success", builderVariable));
        return Optional.of(ctx);
    }
}
