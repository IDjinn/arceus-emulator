package packets.outgoing.rooms.objects.wall;

import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RemoveWallItemComposer extends OutgoingPacket {

    public RemoveWallItemComposer(IWallItem wallItem, int pickupPlayerId) {
        super(OutgoingHeaders.RemoveWallItemComposer);
        appendString(String.valueOf(wallItem.getVirtualId()));
        appendInt(pickupPlayerId);
    }
}
