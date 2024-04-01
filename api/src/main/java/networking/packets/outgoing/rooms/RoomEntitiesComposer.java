package networking.packets.outgoing.rooms;

import habbohotel.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

import java.util.List;

public class RoomEntitiesComposer extends OutgoingPacket {
    public RoomEntitiesComposer(IRoomEntity entity) {
        super(OutgoingHeaders.RoomEntitiesComposer);
        appendInt(1);
        entity.serialize(this);
    }

    public RoomEntitiesComposer(List<IRoomEntity> entities) {
        super(OutgoingHeaders.RoomEntitiesComposer);
        appendInt(entities.size());
        for (IRoomEntity entity : entities) {
            entity.serialize(this);
        }
    }
}
