package packets.outgoing.rooms.prepare;

import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

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

        appendInt(allItems.size()); // TODO WALLITEM LIMITED DATA?
        for (var item : allItems) {
            item.serialize(this);
            item.serializePosition(this);
            item.serializeExtraData(this);
            appendInt(-1);

            appendInt(item.canUse() ? 1 : 0);
            if (item.getOwnerData().isPresent()) {
                var owner = item.getOwnerData().get();
                appendInt(owner.getId());
//                appendString(owner.getUsername());
            }

            appendInt(-1);
//            appendString("");
        }
    }
}
