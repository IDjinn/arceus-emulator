package packets.outgoing.rooms.objects.floor;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;
import utils.pathfinder.Position;

import java.util.Collection;

public class SlideObjectBundleMessageComposer extends OutgoingPacket {

    public SlideObjectBundleMessageComposer(Position oldPosition, Position nextPosition, Collection<SlideObjectEntry> objects,
                                            int rollerId) {
        super(OutgoingHeaders.SlideObjectBundleMessageComposer);

        this.appendInt(oldPosition.getX());
        this.appendInt(oldPosition.getY());

        this.appendInt(nextPosition.getX());
        this.appendInt(nextPosition.getY());

        this.appendInt(objects.size());
        for (var object : objects) {
            this.appendInt(object.virutalId);
            this.appendString(String.valueOf(object.oldPosition().getZ()));
            this.appendString(String.valueOf(object.newPosition().getZ()));
        }
        this.appendInt(rollerId);
    }

    public SlideObjectBundleMessageComposer(
            SlideObjectEntry entityEntry,
            RollerMovementType movementType,
            Position oldPosition, Position nextPosition,
            Collection<SlideObjectEntry> objects,
            int rollerId
    ) {
        this(oldPosition, nextPosition, objects, rollerId);

        this.appendInt(movementType.ordinal());
        this.appendInt(entityEntry.virutalId);
        this.appendString(String.valueOf(oldPosition.getZ()));
        this.appendString(String.valueOf(nextPosition.getZ()));
    }

    public enum RollerMovementType {
        None,
        Move,
        Slide
    }
    public record SlideObjectEntry(int virutalId, Position oldPosition, Position newPosition) {
    }
}
