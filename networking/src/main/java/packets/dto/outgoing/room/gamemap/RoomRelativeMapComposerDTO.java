package packets.dto.outgoing.room.gamemap;

import habbo.rooms.components.gamemap.IRoomGameMap;
import networking.packets.IPacketDTO;

public record RoomRelativeMapComposerDTO(IRoomGameMap roomGameMap) implements IPacketDTO {
}
