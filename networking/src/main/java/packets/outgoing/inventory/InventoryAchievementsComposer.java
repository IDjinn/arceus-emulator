package packets.outgoing.inventory;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class InventoryAchievementsComposer extends OutgoingPacket {
    public InventoryAchievementsComposer() {
        super(OutgoingHeaders.InventoryAchievementsComposer);
        appendInt(0);
    }
}
