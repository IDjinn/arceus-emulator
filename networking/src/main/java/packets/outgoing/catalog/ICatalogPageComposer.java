package packets.outgoing.catalog;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.catalog.CatalogPageComposerDTO;

public interface ICatalogPageComposer extends IOutgoingEvent , networking.packets.outgoing.IOutgoingDTOSerializer<CatalogPageComposerDTO>{
}
