package habbo.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.habbos.IHabbo;
import habbo.habbos.IHabboManager;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomManager;
import habbo.rooms.entities.IRoomEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class CommandHelpers implements ICommandHelpers {
    private static final String HABBO_MENTION_PREFIX = "@";
    private static final String ROOM_MENTION_PREFIX = "#";

    private final IHabboManager habboManager;
    private final IRoomManager roomManager;

    @Inject
    public CommandHelpers(IHabboManager habboManager, IRoomManager roomManager) {
        this.habboManager = habboManager;
        this.roomManager = roomManager;
    }


    @Override
    public Optional<IHabbo> resolveHabbo(@NotNull final String resolvable) {
        if (resolvable.startsWith(HABBO_MENTION_PREFIX)) {
            final var nameWithoutPrefix = resolvable.substring(HABBO_MENTION_PREFIX.length());
            final var habboWithoutPrefix = this.habboManager.getOnlineHabboByUsername(nameWithoutPrefix);
            if (habboWithoutPrefix.isPresent()) {
                return habboWithoutPrefix;
            }

            return this.habboManager.getOnlineHabboByUsername(resolvable);
        }


        return Optional.empty();
    }

    @Override
    public Optional<IRoomEntity> resolveEntity(@NotNull final IRoom room, @NotNull final String resolvable) {
        if (resolvable.startsWith(HABBO_MENTION_PREFIX))
            return this.resolveHabbo(resolvable).flatMap(habbo -> Optional.ofNullable(habbo.getPlayerEntity()));

        for (final IRoomEntity entity : room.getEntityManager().getEntities()) { // TODO CULTURE INVARIANT, IGNORE 
            // CASE ETC
            if (entity.getName().equals(resolvable))
                return Optional.of(entity);
        }

        return Optional.empty();
    }

    @Override
    public Optional<IRoom> resolveRoom(@NotNull final String resolvable) {
        if (resolvable.startsWith(ROOM_MENTION_PREFIX)) {
            final var nameWithoutPrefix = resolvable.substring(ROOM_MENTION_PREFIX.length());
            return this.roomManager.getLoadedRoomsBy(room -> room.getData().getName().equals(nameWithoutPrefix)).stream().findAny();
        }

        return this.roomManager.getLoadedRoomsBy(room -> room.getData().getName().equals(resolvable)).stream().findAny();
    }
}
