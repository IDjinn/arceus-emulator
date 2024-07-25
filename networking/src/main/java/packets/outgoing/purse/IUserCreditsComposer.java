package packets.outgoing.purse;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.purse.UserCreditsComposerDTO;

public interface IUserCreditsComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<UserCreditsComposerDTO> {
}
