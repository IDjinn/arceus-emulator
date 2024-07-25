package packets.outgoing.rooms.objects.wall;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.room.items.wall.RoomWallItemsComposerDTO;

public interface IRoomWallItemsComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<RoomWallItemsComposerDTO>{
}
