package packets.dto.outgoing.room.data;

import networking.packets.IPacketDTO;

public record RoomPaintComposerDTO(String type, String value) implements IPacketDTO {
}
