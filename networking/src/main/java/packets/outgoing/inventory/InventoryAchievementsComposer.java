package packets.outgoing.inventory;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class InventoryAchievementsComposer extends OutgoingPacket {
    public InventoryAchievementsComposer() {
        super(OutgoingHeaders.InventoryAchievementsComposer);
        this.appendInt(0);
    }
}
