package packets.outgoing.rooms.prepare;

import habbo.rooms.components.objects.items.floor.IFloorObject;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.Collection;
import java.util.List;

public class RoomFloorItemsComposer extends OutgoingPacket {
    public RoomFloorItemsComposer(List<String> owners, Collection<IFloorObject> allItems) {
        super(OutgoingHeaders.RoomFloorItemsComposer);
        appendInt(owners.size());
        for (var i = 0; i < owners.size(); i++) {
            appendInt(i);
            appendString(owners.get(i));
        }

        appendInt(allItems.size());
        for (var item : allItems) {
            item.serialize(this);
            item.serializePosition(this);

            appendInt(1); // TODO

            if (item.isLimited()) {
                appendInt(256);
                item.serializeExtraData(this);
                item.serializeLimitedData(this);
            } else {
                appendInt(0);
                item.serializeExtraData(this);
            }

            appendInt(-1);
            appendInt(2); // TODO:FURNITURE USAGE
            appendInt(item.getItemData().getOwnerId());

//            appendString("ownername");  // TODO
        }
    }
}
