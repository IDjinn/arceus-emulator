package packets.outgoing.inventory;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class InventoryRefreshComposer extends OutgoingPacket {
    public InventoryRefreshComposer() {
        super(OutgoingHeaders.InventoryRefreshComposer);
    }
}
