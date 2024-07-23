package packets.outgoing.rooms.entities;

import habbo.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.Collection;

public class RoomUserStatusComposer extends OutgoingPacket {
    public RoomUserStatusComposer(IRoomEntity entity) {
        super(OutgoingHeaders.RoomUserStatusComposer);
        this.appendInt(1);
        this.serializeEntity(entity);
    }

    public RoomUserStatusComposer(Collection<IRoomEntity> entities) {
        super(OutgoingHeaders.RoomUserStatusComposer);
        this.appendInt(entities.size());
        for (var entity : entities)
            this.serializeEntity(entity);
    }

    private void serializeEntity(IRoomEntity entity) {
        this.appendInt(entity.getVirtualId());
        this.appendInt(entity.getPosition().getX());
        this.appendInt(entity.getPosition().getY());
        this.appendString(String.valueOf(entity.getPosition().getZ()));

        this.appendInt(entity.getDirection().ordinal());
        this.appendInt(entity.getDirection().ordinal()); // TODO: HEAD|BODY ROTATION & STATUS

        entity.getStatusComponent().serialize(this);
    }
}
