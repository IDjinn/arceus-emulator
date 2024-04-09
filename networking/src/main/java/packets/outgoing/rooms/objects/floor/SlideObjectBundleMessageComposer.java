package packets.outgoing.rooms.objects.floor;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;
import utils.Position;

import java.util.List;

public class SlideObjectBundleMessageComposer extends OutgoingPacket {

    public SlideObjectBundleMessageComposer(Position oldPosition, Position nextPosition, List<SlideObjectEntry> objects, int rollerId) {
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

    public record SlideObjectEntry(int virutalId, Position oldPosition, Position newPosition) {
    }
}
