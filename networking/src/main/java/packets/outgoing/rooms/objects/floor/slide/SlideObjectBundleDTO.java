package packets.outgoing.rooms.objects.floor.slide;

import networking.packets.IPacketDTO;
import utils.pathfinder.Position;

import java.util.Collection;
import java.util.Objects;

public class SlideObjectBundleDTO implements IPacketDTO {
    private final Position oldPosition;
    private final Position newPosition;
    private final Collection<SlideObjectEntry> objects;
    private final int rollerId;

    public SlideObjectBundleDTO(Position oldPosition, Position newPosition, Collection<SlideObjectEntry> objects,
                                int rollerId) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.objects = objects;
        this.rollerId = rollerId;
    }

    public Position oldPosition() {
        return oldPosition;
    }

    public Position newPosition() {
        return newPosition;
    }

    public Collection<SlideObjectEntry> objects() {
        return objects;
    }

    public int rollerId() {
        return rollerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SlideObjectBundleDTO) obj;
        return Objects.equals(this.oldPosition, that.oldPosition) &&
                Objects.equals(this.newPosition, that.newPosition) &&
                Objects.equals(this.objects, that.objects) &&
                this.rollerId == that.rollerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldPosition, newPosition, objects, rollerId);
    }

    @Override
    public String toString() {
        return "SlideObjectBundleDTO[" +
                "oldPosition=" + oldPosition + ", " +
                "newPosition=" + newPosition + ", " +
                "objects=" + objects + ", " +
                "rollerId=" + rollerId + ']';
    }
}
