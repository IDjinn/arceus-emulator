package habbo.commands;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.internationalization.LocalizedString;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;
import org.jetbrains.annotations.NotNull;
import utils.StringBuilderHelper;

import java.util.Optional;

public class CommandContext implements ICommandContext {
    @Inject
    private ICommandHelpers commandHelpers;

    private final IPlayerEntity player;
    private final String commandName;
    private final String[] arguments;
    private int currentArg;

    public CommandContext(@NotNull IPlayerEntity player, @NotNull String commandName, @NotNull String[] arguments) {
        this.player = player;
        this.commandName = commandName;
        this.arguments = arguments;
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
    public IPlayerEntity getPlayer() {
        return this.player;
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
    public void whisper(final LocalizedString message) {
        this.player.getRoom().getEntityManager().whisper(this.player, message, 0);
    }

    @Override
    public void shout(final LocalizedString message) {
        this.player.getRoom().getEntityManager().shout(this.player, message, 0);
    }

    @Override
    public void talk(final LocalizedString message) {
        this.player.getRoom().getEntityManager().talk(this.player, message, 0);
    }
}
