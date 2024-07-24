package habbo.furniture.extra.data;

import habbo.rooms.components.objects.items.ILimitedData;
import networking.packets.IPacketWriter;

public interface IExtraData {
    ExtraDataType getExtraDataType();

    void serializeState(IPacketWriter writer);

    ILimitedData getLimitedData();

    void setLimitedData(ILimitedData data);

    String toJson();

    int getState();

    void setState(final int state);
}
