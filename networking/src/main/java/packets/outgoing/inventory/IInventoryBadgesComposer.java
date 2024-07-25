package packets.outgoing.inventory;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.inventory.InventoryBadgesComposerDTO;

public interface IInventoryBadgesComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<InventoryBadgesComposerDTO> {
}
