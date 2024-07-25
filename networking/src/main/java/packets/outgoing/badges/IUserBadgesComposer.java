package packets.outgoing.badges;

import networking.packets.IPacketDTO;
import networking.packets.outgoing.IOutgoingDTOSerializer;
import networking.packets.outgoing.IOutgoingEvent;

public interface IUserBadgesComposer extends IOutgoingEvent, IOutgoingDTOSerializer<IPacketDTO> {
}
