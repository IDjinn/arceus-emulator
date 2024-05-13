package habbo.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import core.events.EventListener;
import core.events.EventListenerPriority;
import habbo.commands.generic.AboutCommand;
import habbo.habbos.IHabbo;
import habbo.internationalization.IInternationalizationManager;
import habbo.internationalization.LocalizedString;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.events.RoomEntityTalkEvent;
import io.netty.util.internal.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements ICommandManager {
    private static final Logger logger = LogManager.getLogger();
    private final String prefix = ":";
    private final Map<String, ICommand> commands;
    private final IInternationalizationManager internationalizationManager;
    private final Injector injector;
    private IRoom room;

    @Inject
    public CommandManager(
            final IInternationalizationManager internationalizationManager,
            final Injector injector
    ) {
        this.internationalizationManager = internationalizationManager;
        this.injector = injector;
        this.commands = new HashMap<>();

        this.registerCommand(new AboutCommand());
    }

    @Override
    public Map<String, ICommand> getCommands() {
        return this.commands;
    }

    @Override
    public boolean isCommand(@NotNull final String message) {
        return !StringUtil.isNullOrEmpty(message) && message.startsWith(this.prefix);
    }

    @Override
    public void registerCommand(@NotNull final ICommand command) {
        this.injector.injectMembers(command);
        for (final String localizedName : this.internationalizationManager.getAllLocalizedStrings(command.getName())) {
            this.commands.putIfAbsent(localizedName, command);
        }

        for (final LocalizedString alias : command.getAlias()) {
            for (final String localizedAlias : this.internationalizationManager.getAllLocalizedStrings(alias)) {
                this.commands.putIfAbsent(localizedAlias, command);
            }
        }
    }

    @Override
    public void unregisterCommand(@NotNull final ICommand command) {
//        this.commands.remove(command.getName()); TODO FIX THIS
    }

    @Override
    public void execute(@NotNull IHabbo habbo, @NotNull final String message) {
        if (habbo.getPlayerEntity() == null)
            return;

        if (StringUtil.isNullOrEmpty(message))
            return;

        final var split = message.split(" ");
        final var commandName = split[0].substring(this.prefix.length());
        final var command = this.commands.get(commandName);
        if (command == null) return; // TODO: COMMAND NOT FOUND?

        final var parameters = new String[split.length - 1];
        System.arraycopy(split, 1, parameters, 0, split.length - 1);

        try {
            final var ctx = new CommandContext(habbo.getPlayerEntity(), split[0], parameters);
            this.injector.injectMembers(ctx);
            command.execute(ctx);
        } catch (Exception e) {
            logger.error("error while executing command {}", command.getName(), e);
        }
    }

    @EventListener(priority = EventListenerPriority.High, listenCancelled = false)
    public void onEntityTalkEvent(RoomEntityTalkEvent entityTalkEvent) {
        if (!(entityTalkEvent.entity() instanceof IPlayerEntity player)) return;

        if (this.getRoom().getCommandManager().isCommand(entityTalkEvent.message())) {
            this.getRoom().getCommandManager().execute(player.getHabbo(), entityTalkEvent.message());
            entityTalkEvent.setCancelled(true);
        }
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public void init(final IRoom room) {
        this.room = room;

        this.getRoom().getEventHandler().subscribe(this);
    }

    @Override
    public void destroy() {
        this.getRoom().getEventHandler().unsubscribe(this);
    }
}