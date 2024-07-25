package packets.outgoing.inventory;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.inventory.InventoryItemsComposerDTO;

public interface IInventoryItemsComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<InventoryItemsComposerDTO> {
}
