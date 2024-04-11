package habbo.furniture;

import utils.interfaces.IDisposable;
import utils.interfaces.IFillable;

public interface IFurniture extends IDisposable, IFillable {

    int getId();

    int getSpriteId();

    String getPublicName();

    String getItemName();

    FurnitureType getType();

    int getWidth();

    int getLength();

    double getStackHeight();

    String getInteractionType();

    int getInteractionModesCount();

    boolean isCanSit();

    boolean isCanWalk();

    boolean isCanLay();

    boolean isCanStack();

    boolean isCanGift();

    boolean isCanTrade();

    boolean isCanRecycle();

    boolean isCanSellOnMarketplace();

    boolean isCanStackOnInventory();
}