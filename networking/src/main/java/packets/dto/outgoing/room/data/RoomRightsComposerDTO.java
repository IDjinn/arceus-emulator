package packets.dto.outgoing.room.data;

import habbo.rooms.RoomRightLevel;
import networking.packets.IPacketDTO;

public record RoomRightsComposerDTO(RoomRightLevel rightLevel) implements IPacketDTO {
}
