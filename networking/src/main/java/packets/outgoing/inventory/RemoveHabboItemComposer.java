package packets.outgoing.inventory;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RemoveHabboItemComposer extends OutgoingPacket {
    public RemoveHabboItemComposer(Integer itemId) {
        super(OutgoingHeaders.RemoveHabboItemComposer);
        this.appendInt(itemId);
    }
}
