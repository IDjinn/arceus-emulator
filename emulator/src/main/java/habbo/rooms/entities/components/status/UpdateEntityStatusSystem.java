package habbo.rooms.entities.components.status;

import core.ecs.IDomain;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;

import java.util.HashSet;

public class UpdateEntityStatusSystem implements Runnable{
    private final IDomain domain;

    public UpdateEntityStatusSystem(IDomain domain) {
        this.domain = domain;
    }

    @Override
    public void run() {
        final var toRemove = new HashSet<RoomEntityStatus>();

        this.domain.getDominion().findEntitiesWith(EntityStatusComponent.class).stream().forEach(result ->{
            var entityStatusComponent = result.comp();
            for (final var bucket : entityStatusComponent.statusBuckets.values()) {
                if (toRemove.contains(bucket.getStatus()))
                    continue;

                final var excludes = bucket.getStatus().getExcludes();
                toRemove.addAll(entityStatusComponent.statusBuckets.values().stream()
                        .map(StatusBucket::getStatus)
                        .filter(excludes::contains).toList()
                );

                if (bucket.getTicks().isEmpty()) continue;
                if (bucket.getTicks().get() <= 0)
                    toRemove.add(bucket.getStatus());

                bucket.decrementTick();
            }

            entityStatusComponent.needUpdateStatus = (!toRemove.isEmpty());
            for (final var status : toRemove) {
                entityStatusComponent.statusBuckets.remove(status);
            }
        });
    }
}
