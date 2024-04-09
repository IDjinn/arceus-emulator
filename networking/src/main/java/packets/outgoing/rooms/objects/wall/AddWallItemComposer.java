package packets.outgoing.rooms.objects.wall;

import habbo.furniture.FurnitureUsagePolicy;
import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class AddWallItemComposer extends OutgoingPacket {

    public AddWallItemComposer(IWallItem wallItem) {
        super(OutgoingHeaders.AddWallItemComposer);
        wallItem.serializeItemIdentity(this);
        wallItem.serializePosition(this);
        wallItem.getExtraData().serializeState(this);
        appendInt(-1, "rent?");
        appendInt(FurnitureUsagePolicy.Everyone.ordinal()); // TODO
        appendInt(wallItem.getOwnerData().isPresent() ? wallItem.getOwnerData().get().getId() : -1);
        appendString(wallItem.getOwnerData().isPresent() ? wallItem.getOwnerData().get().getUsername() : "");
    }
}
