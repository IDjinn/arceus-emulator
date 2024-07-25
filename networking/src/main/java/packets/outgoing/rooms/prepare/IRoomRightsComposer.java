package packets.outgoing.rooms.prepare;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.room.data.RoomRightsComposerDTO;

public interface IRoomRightsComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<RoomRightsComposerDTO>{
}
