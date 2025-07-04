package habbo.furniture;

import lombok.Getter;
import storage.results.IConnectionResult;

@Getter
public class Furniture implements IFurniture {
    private int id;
    private int spriteId;
    private String publicName;
    private String itemName;
    private FurnitureType type;
    private int width;
    private int length;
    private double stackHeight;
    private String interactionType;
    private int interactionModesCount;

    private boolean canSit;
    private boolean canWalk;
    private boolean canLay;
    private boolean canStack;
    private boolean canGift;
    private boolean canTrade;
    private boolean canRecycle;
    private boolean canSellOnMarketplace;
    private boolean canStackOnInventory;

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.spriteId = result.getInt("sprite_id");
        this.publicName = result.getString("public_name");
        this.itemName = result.getString("item_name");
        this.type = FurnitureType.fromString(result.getString("type"));
        this.width = result.getInt("width");
        this.length = result.getInt("length");
        this.stackHeight = result.getDouble("stack_height");

        this.interactionType = result.getString("interaction_type");
        this.interactionModesCount = result.getInt("interaction_modes_count");

        this.canSit = result.getBoolean("allow_sit");
        this.canWalk = result.getBoolean("allow_walk");
        this.canLay = result.getBoolean("allow_lay");
        this.canStack = result.getBoolean("allow_stack");
        this.canGift = result.getBoolean("allow_gift");
        this.canTrade = result.getBoolean("allow_trade");
        this.canRecycle = result.getBoolean("allow_recycle");
        this.canSellOnMarketplace = result.getBoolean("allow_marketplace_sell");
        this.canStackOnInventory = result.getBoolean("allow_inventory_stack");
    }
}
