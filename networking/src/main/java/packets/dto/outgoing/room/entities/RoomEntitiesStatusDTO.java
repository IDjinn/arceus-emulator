package packets.dto.outgoing.room.entities;

import habbo.rooms.entities.IRoomEntity;
import networking.packets.IPacketDTO;

import java.util.List;

public record RoomEntitiesStatusDTO(List<IRoomEntity> entities) implements IPacketDTO {
    public static RoomEntitiesStatusDTO of(IRoomEntity entity) {
        return new RoomEntitiesStatusDTO(List.of(entity));
    }
}
