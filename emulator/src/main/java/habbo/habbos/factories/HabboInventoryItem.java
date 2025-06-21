package habbo.habbos.factories;

import com.google.inject.Inject;
import habbo.furniture.FurnitureType;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.IHabbo;
import habbo.habbos.inventory.IHabboInventoryItem;
import habbo.rooms.components.objects.items.LimitedData;
import lombok.Getter;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;

@Getter
public class HabboInventoryItem implements IHabboInventoryItem {
    private int id;
    private IFurniture furniture;
    private IExtraData extraData;
    private @Nullable String wiredData;
    private int group;
    private final IHabbo habbo;

    public HabboInventoryItem(IHabbo habbo) {
        this.habbo = habbo;
    }

    public HabboInventoryItem(IHabbo habbo,
                              int id,
                              IFurniture furniture,
                              IExtraData extraData,
                              @Nullable String wiredData,
                              int group) {
        this.habbo = habbo;
        this.id = id;
        this.furniture = furniture;
        this.extraData = extraData;
        this.wiredData = wiredData;
        this.group = group;
    }

    @Inject
    private IFurnitureManager furnitureManager;

    @Override
    public int getGroup() {
        return this.group;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.furniture = this.furnitureManager.get(result.getInt("item_id"));
        if (this.furniture == null) {
            throw new IllegalArgumentException(
                    "Invalid furniture base id for item id " + this.id
            );
        }

        this.extraData = this.furnitureManager.parseExtraData(result.getString("extra_data"));
        this.extraData.setLimitedData(
                LimitedData.fromString(result.getString("limited_data"))
        );
        this.wiredData = result.getString("wired_data");
        this.group     = result.getInt("guild_id");
    }

    /*
    export class FurniCategory
{
    public static DEFAULT: number = 1;
    public static WALL_PAPER: number = 2;
    public static FLOOR: number = 3;
    public static LANDSCAPE: number = 4;
    public static POST_IT: number = 5;
    public static POSTER: number = 6;
    public static SOUND_SET: number = 7;
    public static TRAX_SONG: number = 8;
    public static PRESENT: number = 9;
    public static ECOTRON_BOX: number = 10;
    public static TROPHY: number = 11;
    public static CREDIT_FURNI: number = 12;
    public static PET_SHAMPOO: number = 13;
    public static PET_CUSTOM_PART: number = 14;
    public static PET_CUSTOM_PART_SHAMPOO: number = 15;
    public static PET_SADDLE: number = 16;
    public static GUILD_FURNI: number = 17;
    public static GAME_FURNI: number = 18;
    public static MONSTERPLANT_SEED: number = 19;
    public static MONSTERPLANT_REVIVAL: number = 20;
    public static MONSTERPLANT_REBREED: number = 21;
    public static MONSTERPLANT_FERTILIZE: number = 22;
    public static FIGURE_PURCHASABLE_SET: number = 23;
}
     */

    @Override
    public void serialize(OutgoingPacket packet) {
        packet.appendInt(this.id)
                .appendString(this.furniture.getType().toString())
                .appendInt(this.id, "_ref")
                .appendInt(this.furniture.getSpriteId())
                .appendInt(1, "category"); // TODO THIS, USED TO SELECTOR WIREDS

        this.getExtraData().serialize(packet);

        packet.appendBoolean(false, "_isRecyclable") // TODO 
                .appendBoolean(true, "_tradable")
                .appendBoolean(!this.getExtraData().getLimitedData().isLimited(), "_isGroupable (inventory stack?)")
                .appendBoolean(false, "_sellable")

                .appendInt(-1, "_secondsToExpiration")
                .appendBoolean(false, "_hasRentPeriodStarted")
                .appendInt(-1, "_flatId"); // TODO

        if (this.furniture.getType().equals(FurnitureType.FLOOR)) {
            packet.appendString("", "_slotId");
            packet.appendInt(-1, "_extra");
        }
    }
}
