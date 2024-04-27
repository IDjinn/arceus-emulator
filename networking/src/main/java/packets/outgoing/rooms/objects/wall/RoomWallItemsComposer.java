package packets.outgoing.rooms.objects.wall;

import habbo.furniture.FurnitureUsagePolicy;
import habbo.rooms.components.objects.items.wall.IWallItem;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

import java.util.Collection;
import java.util.List;

public class RoomWallItemsComposer extends OutgoingPacket {
    public RoomWallItemsComposer(List<String> owners, Collection<IWallItem> allItems) {
        super(OutgoingHeaders.RoomWallItemsComposer);
        appendInt(owners.size());
        for (var i = 0; i < owners.size(); i++) {
            appendInt(i);
            appendString(owners.get(i));
        }

        appendInt(allItems.size());
        for (var item : allItems) {
            item.serializeItemIdentity(this);
            item.serializePosition(this);
            item.getExtraData().serializeState(this);
            appendInt(-1, "expiration timeout");
            appendInt(FurnitureUsagePolicy.Controller.ordinal()); // TODO:FURNITURE USAGE

            if (item.getOwnerData() != null && item.getOwnerData().isPresent()) {
                var owner = item.getOwnerData().get();
                appendInt(owner.getId());
            } else {
                appendInt(0);
            }
        }
    }
}
