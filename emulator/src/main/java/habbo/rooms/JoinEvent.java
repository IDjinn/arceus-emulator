package habbo.rooms;

import habbo.habbos.IHabbo;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class JoinEvent {
    private final IRoom room;
    private final IHabbo habbo;
    private final @Nullable String password;

    public JoinEvent(IRoom room, IHabbo habbo, @Nullable String password) {
        this.room = room;
        this.habbo = habbo;
        this.password = password;
    }

    public IRoom getRoom() {
        return room;
    }

    public IHabbo getHabbo() {
        return habbo;
    }

    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (JoinEvent) obj;
        return Objects.equals(this.room, that.room) &&
                Objects.equals(this.habbo, that.habbo) &&
                Objects.equals(this.password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, habbo, password);
    }

    @Override
    public String toString() {
        return "JoinEvent[" +
                "room=" + room + ", " +
                "habbo=" + habbo + ", " +
                "password=" + password + ']';
    }

}
