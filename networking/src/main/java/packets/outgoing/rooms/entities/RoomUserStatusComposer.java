package packets.outgoing.rooms.entities;

import habbo.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;
import utils.StringBuilderHelper;

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

        this.appendInt((int) entity.getDirection().ordinal());
        this.appendInt((int) entity.getDirection().ordinal()); // TODO: HEAD|BODY ROTATION & STATUS

        final var entityStatus = StringBuilderHelper.getBuilder().append('/');
        for (var entry : entity.getStatus().entrySet()) {
            var status = entry.getKey();
            var bucket = entry.getValue();

            entityStatus.append(status.toString());
            entityStatus.append(' ');
            if (bucket.getValue() != null)
                entityStatus.append(bucket.getValue());
            entityStatus.append('/');
        }

        this.appendString(entityStatus.toString());
    }

}
