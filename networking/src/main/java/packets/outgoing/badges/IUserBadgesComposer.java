package packets.outgoing.badges;

import networking.packets.IPacketDTO;
import networking.packets.outgoing.IOutgoingDTOSerializer;
import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.badges.UserBadgesDTO;

public interface IUserBadgesComposer extends IOutgoingEvent, IOutgoingDTOSerializer<UserBadgesDTO> {
}
