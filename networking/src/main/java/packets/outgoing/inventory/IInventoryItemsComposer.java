package packets.outgoing.inventory;

import networking.packets.outgoing.IOutgoingEvent;

public interface IInventoryItemsComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<networking.packets.IPacketDTO> {
}
