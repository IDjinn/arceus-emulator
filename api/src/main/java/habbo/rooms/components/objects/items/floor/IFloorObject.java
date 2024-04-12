package habbo.rooms.components.objects.items.floor;

import habbo.rooms.components.gamemap.ITileMetadata;
import habbo.rooms.components.objects.IRoomObject;
import habbo.rooms.components.objects.items.IPositionable;
import org.jetbrains.annotations.NotNull;

public interface IFloorObject extends IRoomObject, IPositionable, Comparable<IFloorObject> {
    @Override
    default int compareTo(@NotNull IFloorObject o) {
        return Double.compare(this.getPosition().getZ(), o.getPosition().getZ());
    }

    double getHeight();

    boolean canSlideTo(ITileMetadata metadata);
}
