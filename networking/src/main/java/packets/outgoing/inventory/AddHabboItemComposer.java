package packets.outgoing.inventory;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.List;

public class AddHabboItemComposer extends OutgoingPacket {
    public AddHabboItemComposer(AddHabboItemCategory category, List<Integer> itemIds) {
        super(OutgoingHeaders.AddHabboItemComposer);

        appendInt(1, "total unseen categories");
        appendInt(category.ordinal());
        appendInt(itemIds.size());
        for (int itemId : itemIds) {
            appendInt(itemId);
        }
    }
}
