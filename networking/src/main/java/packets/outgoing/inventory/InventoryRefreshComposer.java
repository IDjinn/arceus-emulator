package packets.outgoing.inventory;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class InventoryRefreshComposer extends OutgoingPacket {
    public InventoryRefreshComposer() {
        super(OutgoingHeaders.InventoryRefreshComposer);
    }
}
