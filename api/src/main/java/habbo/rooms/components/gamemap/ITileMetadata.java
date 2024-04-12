package habbo.rooms.components.gamemap;

import habbo.rooms.components.objects.items.floor.IFloorItem;
import stormpot.Poolable;

import javax.annotation.Nullable;
import java.util.Optional;

public interface ITileMetadata extends Poolable, Comparable<ITileMetadata> {
    IRoomTile getRoomTile();

    void setRoomTile(final IRoomTile roomTile);

    Optional<Double> getHeight();

    Optional<Double> getEntityHeight();

    Optional<Double> getWalkableHeight();

    void setWalkableHeight(@Nullable final Double walkableHeight);

    Optional<Double> getSitHeight();

    void setSitHeight(@Nullable final Double sitHeight);

    Optional<Double> getLayHeight();

    void setLayHeight(@Nullable final Double layHeight);

    Optional<Double> getStackableHeight();

    Optional<IFloorItem> getItem();

    void setItem(@Nullable final IFloorItem item);

    void setStackHeight(@Nullable final Double stackHeight);
}