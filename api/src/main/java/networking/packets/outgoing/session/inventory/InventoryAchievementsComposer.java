package networking.packets.outgoing.session.inventory;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class InventoryAchievementsComposer extends OutgoingPacket {
    public InventoryAchievementsComposer() {
        super(OutgoingHeaders.InventoryAchievementsComposer);
        appendInt(0);
    }
}
