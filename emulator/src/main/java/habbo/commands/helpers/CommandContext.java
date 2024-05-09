package habbo.commands.helpers;

import com.google.inject.Inject;
import habbo.commands.ICommandContext;
import habbo.commands.ICommandHelpers;
import habbo.commands.helpers.arguments.ArgumentType;
import habbo.commands.helpers.arguments.ICommandArgument;
import habbo.habbos.IHabbo;
import habbo.internationalization.LocalizedString;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;
import networking.client.IClient;
import org.jetbrains.annotations.NotNull;
import utils.StringBuilderHelper;

import java.util.Optional;
import java.util.function.Function;

public class CommandContext implements ICommandContext {
    @Inject
    private ICommandHelpers commandHelpers;

    private final IPlayerEntity player;
    private final String commandName;
    private final String[] arguments;
    private int currentArg;
    private boolean isError;

    public CommandContext(@NotNull IPlayerEntity player, @NotNull String commandName, @NotNull String[] arguments) {
        this.player = player;
        this.commandName = commandName;
        this.arguments = arguments;
    }

    @Override
    public IPlayerEntity getPlayerEntity() {
        return this.player;
    }

    @Override
    public IClient getClient() {
        return this.getHabbo().getClient();
    }

    @Override
    public IHabbo getHabbo() {
        return this.getPlayerEntity().getHabbo();
    }

    @Override
    public IRoom getRoom() {
        return this.getPlayerEntity().getRoom();
    }

    @Override
    public Optional<ICommandArgument> required(final String parameterName, final ArgumentType argumentType) {
        return Optional.empty();
    }

    @Override
    public <T extends ICommandArgument, TResult> Optional<TResult> optional(final String parameterName, final ArgumentType argumentType, final Class<T> argument) {
        return Optional.empty();
    }

    @Override
    public int getCurrentArg() {
        return this.currentArg;
    }

    @Override
    public void setCurrentArg(final int currentArg) {
        this.currentArg = currentArg;
    }

    @Override
    public Optional<Object> match(final ArgumentType argumentType, final Function<ICommandArgument, Optional<Object>> callback) {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> popInt() {
        return this.popArg().map(Integer::parseInt);
    }

    @Override
    public Optional<Double> popDouble() {
        return this.popArg().map(Double::parseDouble);
    }

    @Override
    public Optional<String> popArg() {
        return this.currentArg < this.arguments.length ? Optional.of(this.arguments[this.currentArg++]) : Optional.empty();
    }

    @Override
    public Optional<IHabbo> popHabbo() {
        return this.popArg().flatMap(this.commandHelpers::resolveHabbo);
    }

    @Override
    public Optional<IRoomEntity> popEntity() {
        return this.popArg().flatMap(arg -> this.commandHelpers.resolveEntity(this.player.getRoom(), arg));
    }

    @Override
    public Optional<IPlayerEntity> popPlayerEntity(final String parameterName) {
        final var entity = this.popEntity();
        if (entity.isPresent() && entity.get() instanceof IPlayerEntity)
            return Optional.of((IPlayerEntity) entity.get());
        return Optional.empty();
    }

    @Override
    public Optional<IRoom> popRoom() {
        return this.popArg().flatMap(this.commandHelpers::resolveRoom);
    }

    @Override
    public Optional<String> popText() {
        final var count = this.arguments.length - 1 - this.currentArg;
        if (count > 0) {
            final var sb = StringBuilderHelper.getBuilder();
            for (int i = 0; i < count; i++) {
                sb.append(this.arguments[this.currentArg++]);
                if (i < count - 1) {
                    sb.append(" ");
                }
            }
            return Optional.of(sb.toString());
        }
        return Optional.empty();
    }

    @Override
    public Optional<ICommandContext> whisper(final LocalizedString message) {
        this.player.getRoom().getEntityManager().whisper(this.player, message, 0);
        return Optional.of(this);
    }

    @Override
    public void shout(final LocalizedString message) {
        this.player.getRoom().getEntityManager().shout(this.player, message, 0);
    }

    @Override
    public void talk(final LocalizedString message) {
        this.player.getRoom().getEntityManager().talk(this.player, message, 0);
    }

    @Override
    public Optional<ICommandContext> error(final LocalizedString message) {
        this.whisper(message);
        this.isError = true;
        return Optional.of(this);
    }

    @Override
    public Optional<Object> error(final ArgumentType type, final LocalizedString message) {
        return Optional.empty();
    }

    @Override
    public boolean isError() {
        return this.isError;
    }
}
