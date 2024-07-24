package packets.outgoing.rooms.objects.wall;

import habbo.furniture.FurnitureUsagePolicy;
import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.Collection;
import java.util.List;

public class RoomWallItemsComposer extends OutgoingPacket {
    public RoomWallItemsComposer(List<String> owners, Collection<? extends IWallItem> allItems) {
        super(OutgoingHeaders.RoomWallItemsComposer);
        this.appendInt(owners.size());
        for (var i = 0; i < owners.size(); i++) {
            this.appendInt(i);
            this.appendString(owners.get(i));
        }

        this.appendInt(allItems.size());
        for (var item : allItems) {
            item.serializeItemIdentity(this);
            item.serializePosition(this);
            item.getExtraData().serializeState(this);
            this.appendInt(-1, "expiration timeout");
            this.appendInt(FurnitureUsagePolicy.Controller.ordinal()); // TODO:FURNITURE USAGE

            if (item.getOwnerData() != null && item.getOwnerData().isPresent()) {
                var owner = item.getOwnerData().get();
                this.appendInt(owner.getId());
            } else {
                this.appendInt(0);
            }
        }
    }
}
