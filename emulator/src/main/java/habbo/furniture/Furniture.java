package habbo.furniture;

import storage.results.IConnectionResult;

public class Furniture implements IFurniture {
    private final int id;
    private final int spriteId;
    private final String publicName;
    private final String itemName;
    private final FurnitureType type;
    private final int width;
    private final int length;
    private final double stackHeight;
    private final String interactionType;
    private final int interactionModesCount;

    public Furniture(IConnectionResult result) throws Exception {
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
    public boolean canSit() {
        return false;
    }

    @Override
    public boolean canWalk() {
        return false;
    }

    @Override
    public boolean canLay() {
        return false;
    }
}
