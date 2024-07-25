package packets.outgoing.achievements;

import networking.packets.IPacketDTO;
import networking.packets.outgoing.IOutgoingDTOSerializer;
import networking.packets.outgoing.IOutgoingEvent;

public interface IAchievementListComposer extends IOutgoingEvent, IOutgoingDTOSerializer<IPacketDTO> {
}
