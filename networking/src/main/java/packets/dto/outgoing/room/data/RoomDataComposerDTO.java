package packets.dto.outgoing.room.data;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import networking.packets.IPacketDTO;

public record RoomDataComposerDTO(IRoom room, IHabbo habbo, boolean roomForward, boolean enterRoom) implements IPacketDTO {
}
