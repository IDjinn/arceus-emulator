package habbo.furniture;

import utils.IDisposable;
import utils.IFillable;

public interface IFurniture extends IDisposable, IFillable {

    public int getId();

    public int getSpriteId();

    public String getPublicName();

    public String getItemName();

    public FurnitureType getType();

    public int getWidth();

    public int getLength();

    public double getStackHeight();

    public String getInteractionType();

    public int getInteractionModesCount();

    public boolean isCanSit();

    public boolean isCanWalk();

    public boolean isCanLay();

    public boolean isCanStack();

    public boolean isCanGift();

    public boolean isCanTrade();

    public boolean isCanRecycle();

    public boolean isCanSellOnMarketplace();

    public boolean isCanStackOnInventory();
}