package packets.outgoing.rooms.objects.floor.slide;

import utils.pathfinder.Position;

import java.util.Collection;
import java.util.Objects;

public final class EntitySlideObjectBundleDTO extends SlideObjectBundleDTO {
    private final SlideObjectEntry entityEntry;
    private final RollerMovementType movementType;

    public EntitySlideObjectBundleDTO(SlideObjectEntry entityEntry,
                                      RollerMovementType movementType,
                                      Position oldPosition, Position newPosition,
                                      Collection<SlideObjectEntry> objects,
                                      int rollerId) {
        super(oldPosition, newPosition, objects, rollerId);
        this.entityEntry = entityEntry;
        this.movementType = movementType;
    }

    public SlideObjectEntry entityEntry() {
        return entityEntry;
    }

    public RollerMovementType movementType() {
        return movementType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (EntitySlideObjectBundleDTO) obj;
        return Objects.equals(this.entityEntry, that.entityEntry) &&
                Objects.equals(this.movementType, that.movementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityEntry, movementType);
    }

    @Override
    public String toString() {
        return "EntitySlideObjectBundleDTO[" +
                "entityEntry=" + entityEntry + ", " +
                "movementType=" + movementType + "]";
    }
}
