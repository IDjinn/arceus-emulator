package packets.outgoing.rooms.prepare;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.room.data.RoomDataComposerDTO;

public interface IRoomDataComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<RoomDataComposerDTO>{
}
