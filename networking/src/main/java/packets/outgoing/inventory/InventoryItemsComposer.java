package packets.outgoing.inventory;

import habbo.habbos.inventory.IHabboInventoryItem;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

import java.util.List;

public class InventoryItemsComposer extends OutgoingPacket {
    public InventoryItemsComposer(int fragment, int totalFragments, List<IHabboInventoryItem> items) {
        super(OutgoingHeaders.InventoryItemsComposer);

        appendInt(fragment);
        appendInt(totalFragments - 1);
        appendInt(items.size());
        for (var item : items) {
            item.serialize(this);
        }
    }
}
