package habbo.commands;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IRoomEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ICommandHelpers {
    public Optional<IHabbo> resolveHabbo(@NotNull String resolvable);

    public Optional<IRoomEntity> resolveEntity(@NotNull final IRoom room, @NotNull String resolvable);

    public Optional<IRoom> resolveRoom(@NotNull String resolvable);
}
