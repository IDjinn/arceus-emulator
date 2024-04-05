package packets.outgoing.inventory;

import habbo.habbos.inventory.IHabboInventoryItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.List;

public class InventoryItemsComposer extends OutgoingPacket {
    public InventoryItemsComposer(int pageCount, int totalPages, List<IHabboInventoryItem> items) {
        super(OutgoingHeaders.InventoryItemsComposer);

        appendInt(pageCount);
        appendInt(totalPages);
        appendInt(items.size());
        for (var item : items) {
            item.serialize(this);
        }
    }
}
