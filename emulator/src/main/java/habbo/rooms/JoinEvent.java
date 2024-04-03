package habbo.rooms;

import habbo.habbos.IHabbo;
import org.jetbrains.annotations.Nullable;

public record JoinEvent(IRoom room, IHabbo habbo, @Nullable String password) {
}
