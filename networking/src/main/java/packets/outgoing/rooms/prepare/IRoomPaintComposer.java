package packets.outgoing.rooms.prepare;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.room.data.RoomPaintComposerDTO;

public interface IRoomPaintComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<RoomPaintComposerDTO>{
}
