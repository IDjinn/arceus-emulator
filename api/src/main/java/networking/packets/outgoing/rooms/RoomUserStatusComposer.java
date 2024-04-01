package networking.packets.outgoing.rooms;

import habbohotel.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

import java.util.List;

public class RoomUserStatusComposer extends OutgoingPacket {
    public RoomUserStatusComposer(IRoomEntity entity) {
        super(OutgoingHeaders.RoomUserStatusComposer);
        appendInt(1);
        serializeEntity(entity);
    }

    public RoomUserStatusComposer(List<IRoomEntity> entities) {
        super(OutgoingHeaders.RoomUserStatusComposer);
        appendInt(entities.size());
        for (var entity : entities)
            serializeEntity(entity);
    }

    private void serializeEntity(IRoomEntity entity) {

        appendInt(entity.getVirtualId());
        appendInt(entity.getPosition().getX());
        appendInt(entity.getPosition().getY());
        appendDouble(entity.getPosition().getZ());

        appendInt((int) entity.getDirection().ordinal());
        appendInt((int) entity.getDirection().ordinal()); // TODO: HEAD|BODY ROTATION & STATUS

        final var entityStatus = new StringBuilder(20).append('/');
        for (var entry : entity.getStatus().entrySet()) {
            var status = entry.getKey();
            var bucket = entry.getValue();

            entityStatus.append(status.toString());
            entityStatus.append(' ');
            if (bucket.getValue() != null)
                entityStatus.append(bucket.getValue());
            entityStatus.append('/');
        }

        appendString(entityStatus.toString());
    }

}
