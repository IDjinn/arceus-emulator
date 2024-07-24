package habbo.commands;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IRoomEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ICommandHelpers {
    Optional<IHabbo> resolveHabbo(@NotNull String resolvable);

    Optional<IRoomEntity> resolveEntity(@NotNull final IRoom room, @NotNull String resolvable);

    Optional<IRoom> resolveRoom(@NotNull String resolvable);
}
