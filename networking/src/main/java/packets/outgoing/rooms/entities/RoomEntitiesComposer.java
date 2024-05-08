package packets.outgoing.rooms.entities;

import habbo.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.List;

public class RoomEntitiesComposer extends OutgoingPacket {
    public RoomEntitiesComposer(IRoomEntity entity) {
        super(OutgoingHeaders.RoomEntitiesComposer);
        this.appendInt(1);
        entity.serialize(this);
    }

    public RoomEntitiesComposer(List<IRoomEntity> entities) {
        super(OutgoingHeaders.RoomEntitiesComposer);
        this.appendInt(entities.size());
        for (IRoomEntity entity : entities) {
            entity.serialize(this);
        }
    }
}
