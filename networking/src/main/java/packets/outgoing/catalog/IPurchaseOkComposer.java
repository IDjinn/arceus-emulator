package packets.outgoing.catalog;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.catalog.PurchaseOkComposerDTO;

public interface IPurchaseOkComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<PurchaseOkComposerDTO> {
}
