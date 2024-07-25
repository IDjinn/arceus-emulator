package packets.outgoing.catalog;

import networking.packets.IPacketDTO;
import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.catalog.CatalogIndexComposerDTO;

public interface ICatalogIndexComposer extends IOutgoingEvent , networking.packets.outgoing.IOutgoingDTOSerializer<CatalogIndexComposerDTO>{
}
