package packets.outgoing.rooms.prepare;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.room.data.RoomModelComposerDTO;

public interface IRoomModelComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<RoomModelComposerDTO>{
}
