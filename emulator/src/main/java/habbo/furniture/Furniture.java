package habbo.furniture;

import storage.results.IConnectionResult;

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

    @Override
    public void destroy() {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSpriteId() {
        return spriteId;
    }

    @Override
    public String getPublicName() {
        return publicName;
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public FurnitureType getType() {
        return type;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public double getStackHeight() {
        return stackHeight;
    }

    @Override
    public String getInteractionType() {
        return interactionType;
    }

    @Override
    public int getInteractionModesCount() {
        return interactionModesCount;
    }

    @Override
    public boolean isCanStackOnInventory() {
        return canStackOnInventory;
    }

    @Override
    public boolean isCanSellOnMarketplace() {
        return canSellOnMarketplace;
    }

    @Override
    public boolean isCanRecycle() {
        return canRecycle;
    }

    @Override
    public boolean isCanTrade() {
        return canTrade;
    }

    @Override
    public boolean isCanGift() {
        return canGift;
    }

    @Override
    public boolean isCanStack() {
        return canStack;
    }

    @Override
    public boolean isCanLay() {
        return canLay;
    }

    @Override
    public boolean isCanWalk() {
        return canWalk;
    }

    @Override
    public boolean isCanSit() {
        return canSit;
    }
}
