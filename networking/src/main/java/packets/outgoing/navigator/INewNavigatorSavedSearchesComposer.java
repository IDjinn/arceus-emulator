package packets.outgoing.navigator;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.navigator.NewNavigatorSavedSearchesComposerDTO;

public interface INewNavigatorSavedSearchesComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<NewNavigatorSavedSearchesComposerDTO> {
}
