package packets.outgoing.inventory;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

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
