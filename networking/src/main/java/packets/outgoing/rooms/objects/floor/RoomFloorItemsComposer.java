package packets.outgoing.rooms.objects.floor;

import habbo.furniture.FurnitureUsagePolicy;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.Collection;
import java.util.List;

public class RoomFloorItemsComposer extends OutgoingPacket {
    public RoomFloorItemsComposer(List<String> owners, Collection<IFloorItem> allItems) {
        super(OutgoingHeaders.RoomFloorItemsComposer);
        appendInt(owners.size());
        for (var i = 0; i < owners.size(); i++) {
            appendInt(i);
            appendString(owners.get(i));
        }

        appendInt(allItems.size());
        for (var item : allItems) {
            item.serializeItemIdentity(this);
            item.serializePosition(this);

            appendInt(1, "gift, song or something. It seems to be the extraData state (integer) of legacy data"); // TODO

            item.getExtraData().serialize(this);
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