package habbo.rooms.components.objects.items;

import habbo.rooms.components.gamemap.ITileMetadata;
import org.jetbrains.annotations.NotNull;
import utils.pathfinder.Position;

public interface IFloorObject extends Comparable<IFloorObject> {
    int getVirtualId();

    @Override
    default int compareTo(@NotNull IFloorObject o) {
        return Double.compare(this.getPosition().getZ(), o.getPosition().getZ());
    }

    Position getPosition();

    void setPosition(Position position);

    double getHeight();

    boolean canSlideTo(ITileMetadata metadata);
}
