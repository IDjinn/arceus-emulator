package packets.outgoing.inventory;

import networking.packets.outgoing.IOutgoingEvent;

public interface IInventoryRefreshComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<networking.packets.IPacketDTO> {
}
