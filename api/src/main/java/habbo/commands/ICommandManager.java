package habbo.commands;

import habbo.habbos.IHabbo;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ICommandManager {
    Map<String, ICommand> getCommands();

    boolean isCommand(@NotNull String message);

    void registerCommand(@NotNull ICommand command);

    void unregisterCommand(@NotNull ICommand command);

    void execute(@NotNull IHabbo habbo, @NotNull String message);
}
