package packets.outgoing.catalog;

import networking.packets.outgoing.IOutgoingEvent;

public interface ICatalogPageComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<networking.packets.IPacketDTO>, networking.packets.outgoing.IOutgoingDTOSerializer<networking.packets.IPacketDTO> {
}
