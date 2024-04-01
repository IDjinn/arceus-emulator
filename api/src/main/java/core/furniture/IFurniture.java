package core.furniture;

import utils.IDisposable;

public interface IFurniture extends IDisposable {
    public int getId();
    public String getName();
    public String getPublicName();
}

