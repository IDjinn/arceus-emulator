package habbo.rooms.entities.components.status;

import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.status.IEntityStatusComponent;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import utils.StringBuilderHelper;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityStatusComponent  {
    public final Map<RoomEntityStatus, StatusBucket> statusBuckets = new ConcurrentHashMap<>();
    public boolean needUpdateStatus;
}