package habbo.commands;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoomComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ICommandManager extends IRoomComponent {
    Map<String, ICommand> getCommands();

    boolean isCommand(@NotNull String message);

    void registerCommand(@NotNull ICommand command);

    void unregisterCommand(@NotNull ICommand command);

    void execute(@NotNull IHabbo habbo, @NotNull String message);
}
