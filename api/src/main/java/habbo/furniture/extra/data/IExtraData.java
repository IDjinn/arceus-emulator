package habbo.furniture.extra.data;

import habbo.rooms.components.objects.items.ILimitedData;

public interface IExtraData {
    ExtraDataType getExtraDataType();

    ILimitedData getLimitedData();

    void setLimitedData(ILimitedData data);

    String toJson();

    int getState();

    void setState(final int state);
}
