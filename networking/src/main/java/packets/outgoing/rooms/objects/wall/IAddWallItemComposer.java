package packets.outgoing.rooms.objects.wall;

import networking.packets.outgoing.IOutgoingEvent;
import packets.dto.outgoing.room.items.wall.AddWallItemComposerDTO;

public interface IAddWallItemComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<AddWallItemComposerDTO>{
}
