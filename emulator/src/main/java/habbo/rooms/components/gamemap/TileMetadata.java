package habbo.rooms.components.gamemap;

import habbo.rooms.components.objects.items.floor.IFloorFloorItem;
import org.jetbrains.annotations.NotNull;
import stormpot.Slot;

import javax.annotation.Nullable;
import java.util.Optional;

public class TileMetadata implements ITileMetadata {
    private static final double InvalidHeight = -1d;

    private final Slot slot;
    private IRoomTile roomTile;
    private @Nullable Double walkableHeight;
    private @Nullable Double sitHeight;
    private @Nullable Double layHeight;
    private @Nullable Double stackHeight;
    private @Nullable IFloorFloorItem item;

    public TileMetadata(Slot slot) {
        this.slot = slot;
    }

    @Override
    public IRoomTile getRoomTile() {
        return this.roomTile;
    }

    @Override
    public void setRoomTile(final IRoomTile roomTile) {
        this.roomTile = roomTile;
    }

    @Override
    public Optional<Double> getHeight() {
        return
                this.getWalkableHeight()
                        .or(this::getSitHeight)
                        .or(this::getLayHeight)
                        .or(this::getStackableHeight)
                ;
    }

    @Override
    public Optional<Double> getEntityHeight() {
        return
                this.getWalkableHeight()
                        .or(this::getSitHeight)
                        .or(this::getLayHeight)
                ;
    }

    @Override
    public Optional<Double> getWalkableHeight() {
        return Optional.ofNullable(this.walkableHeight);
    }

    @Override
    public void setWalkableHeight(@Nullable final Double walkableHeight) {
        this.walkableHeight = walkableHeight;
    }

    @Override
    public Optional<Double> getSitHeight() {
        return Optional.ofNullable(this.sitHeight);
    }

    @Override
    public void setSitHeight(@Nullable final Double sitHeight) {
        this.sitHeight = sitHeight;
    }

    @Override
    public Optional<Double> getLayHeight() {
        return Optional.ofNullable(this.layHeight);
    }

    @Override
    public void setLayHeight(@Nullable final Double layHeight) {
        this.layHeight = layHeight;
    }

    @Override
    public Optional<Double> getStackableHeight() {
        return Optional.ofNullable(this.stackHeight);
    }

    @Override
    public Optional<IFloorFloorItem> getItem() {
        return Optional.ofNullable(this.item);
    }

    @Override
    public void setItem(@Nullable final IFloorFloorItem item) {
        this.item = item;
    }

    @Override
    public void setStackHeight(@Nullable final Double stackHeight) {
        this.stackHeight = stackHeight;
    }

    @Override
    public int compareTo(@NotNull final ITileMetadata o) {
        return Double.compare(this.getHeight().orElse(InvalidHeight), o.getHeight().orElse(InvalidHeight));
    }

    @Override
    public void release() {
        this.slot.release(this);
    }
}
