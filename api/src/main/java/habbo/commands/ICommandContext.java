package habbo.commands;

import habbo.commands.arguments.ArgumentType;
import habbo.commands.arguments.ICommandArgument;
import habbo.habbos.IHabbo;
import habbo.internationalization.LocalizedString;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;

import java.util.Optional;
import java.util.function.Function;

public interface ICommandContext {
    int getCurrentArg();

    void setCurrentArg(int currentArg);

    IPlayerEntity getPlayerEntity();

    IHabbo getHabbo();

    IRoom getRoom();

    Optional<ICommandArgument> required(final String parameterName, ArgumentType argumentType);

    <T extends ICommandArgument, TResult> Optional<TResult> optional(
            String parameterName,
            ArgumentType argumentType,
            Class<T> argument
    );

    Optional<Object> match(ArgumentType argumentType, Function<ICommandArgument, Optional<Object>> callback);

    Optional<Integer> popInt();

    Optional<Double> popDouble();

    Optional<String> popArg();

    Optional<IHabbo> popHabbo();

    Optional<IRoomEntity> popEntity();

    Optional<IPlayerEntity> popPlayerEntity(final String parameterName);

    Optional<IRoom> popRoom();

    Optional<String> popText();

    Optional<ICommandContext> whisper(LocalizedString message);

    void shout(LocalizedString message);

    void talk(LocalizedString message);

    Optional<ICommandContext> error(LocalizedString message);

    Optional<Object> error(ArgumentType type, LocalizedString message);

    boolean isError();
}
