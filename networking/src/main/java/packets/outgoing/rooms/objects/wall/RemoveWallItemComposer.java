package packets.outgoing.rooms.objects.wall;

import habbo.rooms.components.objects.items.wall.IWallItem;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RemoveWallItemComposer extends OutgoingPacket {

    public RemoveWallItemComposer(IWallItem wallItem, int pickupPlayerId) {
        super(OutgoingHeaders.RemoveWallItemComposer);
        appendString(String.valueOf(wallItem.getVirtualId()));
        appendInt(pickupPlayerId);
    }
}
