package packets.outgoing.navigator;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.navigator.NewNavigatorMetaDataComposerDTO;

public interface INewNavigatorMetaDataComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<NewNavigatorMetaDataComposerDTO> {
}
