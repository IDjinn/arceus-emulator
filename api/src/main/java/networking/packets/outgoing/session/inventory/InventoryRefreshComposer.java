package networking.packets.outgoing.session.inventory;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class InventoryRefreshComposer extends OutgoingPacket {
    public InventoryRefreshComposer() {
        super(OutgoingHeaders.InventoryRefreshComposer);
    }
}
