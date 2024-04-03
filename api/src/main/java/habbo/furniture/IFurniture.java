package habbo.furniture;

import utils.IDisposable;

public interface IFurniture extends IDisposable {

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
}