package habbo.habbos.factories;

import com.google.inject.Inject;
import habbo.furniture.FurnitureType;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import habbo.habbos.inventory.IHabboInventoryItem;
import habbo.rooms.components.objects.items.ILimitedData;
import habbo.rooms.components.objects.items.LimitedData;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;

public class HabboInventoryItem implements IHabboInventoryItem {
    private int id;
    private FurnitureType type;
    private IFurniture furniture;
    private @Nullable String extraData;
    private ILimitedData limitedData;
    private @Nullable String wiredData;
    private int group;

    @Inject
    private IFurnitureManager furnitureManager;

    @Override
    public int getId() {
        return this.id;
    }


    @Override
    public IFurniture getFurniture() {
        return this.furniture;
    }

    @Override
    public @Nullable String getExtraData() {
        return this.extraData;
    }

    @Override
    public void setExtraData(@Nullable String extraData) {
        this.extraData = extraData;
    }

    @Override
    public ILimitedData getLimitedData() {
        return this.limitedData;
    }

    @Override
    public @Nullable String getWiredData() {
        return this.wiredData;
    }

    @Override
    public int getGroup() {
        return this.group;
    }

    private final int category = 0;

    @Override
    public boolean isLimited() {
        return this.getLimitedData().getLimitedRare() > 0;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.furniture = furnitureManager.get(result.getInt("item_id"));
        if (this.furniture == null)
            throw new IllegalArgumentException(STR."Invalid furniture base id for item id \{this.id}");

        this.extraData = result.getString("extra_data");
        this.limitedData = LimitedData.fromString(result.getString("limited_data"));
        this.wiredData = result.getString("wired_data");
        this.group = result.getInt("guild_id");
    }

    @Override
    public void serialize(OutgoingPacket packet) {
        packet.appendInt(this.id)
                .appendString(this.furniture.getType().toString())
                .appendInt(this.id, "_ref")
                .appendInt(this.furniture.getSpriteId())

                .appendInt(0, "extraData type") // TODO: EXTRA DATA TYPE HANDLER
                .appendInt(0, "(0) size of strings?")
                .appendString("")

                .appendBoolean(false, "_isRecyclable") // TODO 
                .appendBoolean(true, "_tradable")
                .appendBoolean(!this.isLimited(), "_isGroupable (inventory stack?)")
                .appendBoolean(false, "_sellable")

                .appendInt(-1, "_secondsToExpiration")
                .appendBoolean(false, "_hasRentPeriodStarted")
                .appendInt(-1, "_flatId");

        if (this.furniture.getType().equals(FurnitureType.FLOOR)) {
            packet.appendString("", "_slotId");
            packet.appendInt(-1, "_extra");
        }
    }
}
