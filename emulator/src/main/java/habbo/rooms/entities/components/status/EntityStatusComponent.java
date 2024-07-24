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

public final class EntityStatusComponent implements IEntityStatusComponent {
    private final Map<RoomEntityStatus, StatusBucket> statusBuckets = new ConcurrentHashMap<>();
    private boolean needUpdateStatus;
    private @NotNull IRoomEntity entity;

    public void init(final IRoomEntity entity) {
        this.entity = entity;
    }

    @Override
    public Map<RoomEntityStatus, StatusBucket> getStatus() {
        return this.statusBuckets;
    }

    @Override
    public void removeStatus(RoomEntityStatus status) {
        this.needUpdateStatus |= this.statusBuckets.remove(status) != null;
    }

    @Override
    public void setNeedUpdateStatus(boolean needUpdate) {
        this.needUpdateStatus = needUpdate;
    }

    @Override
    public boolean isNeedUpdate() {
        return this.needUpdateStatus;
    }

    @Override
    public void setStatus(StatusBucket bucket) {
        if (this.statusBuckets.containsKey(bucket.getStatus())) {
            var currentBucket = this.statusBuckets.get(bucket.getStatus());
            bucket.getTicks().ifPresent(currentBucket::setTicks);
            currentBucket.setValue(bucket.getValue());
        } else {
            this.statusBuckets.put(bucket.getStatus(), bucket);
        }

        this.setNeedUpdateStatus(true);
    }

    @Override
    public void tick() {
        final var toRemove = new HashSet<RoomEntityStatus>();
        for (final var bucket : this.statusBuckets.values()) {
            if (toRemove.contains(bucket.getStatus()))
                continue;

            final var excludes = bucket.getStatus().getExcludes();
            toRemove.addAll(this.statusBuckets.values().stream()
                    .map(StatusBucket::getStatus)
                    .filter(excludes::contains).toList()
            );

            if (bucket.getTicks().isEmpty()) continue;
            if (bucket.getTicks().get() <= 0)
                toRemove.add(bucket.getStatus());

            bucket.decrementTick();
        }

        this.setNeedUpdateStatus(!toRemove.isEmpty());
        for (final var status : toRemove) {
            this.statusBuckets.remove(status);
        }
    }

    @Override
    public void serialize(final OutgoingPacket<U> packet) {
        final var entityStatus = StringBuilderHelper.getBuilder().append('/');
        for (var bucket : this.getStatus().values()) {
            entityStatus.append(bucket.getStatus().toString());
            entityStatus.append(' ');
            if (bucket.getValue() != null)
                entityStatus.append(bucket.getValue());
            entityStatus.append('/');
        }

        packet.appendString(entityStatus.toString());
    }

    @Override
    public IRoomEntity getEntity() {
        return this.entity;
    }
}