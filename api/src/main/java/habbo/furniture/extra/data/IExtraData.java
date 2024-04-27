package habbo.furniture.extra.data;

import habbo.rooms.components.objects.items.ILimitedData;
import networking.packets.IOutgoingPacket;

public interface IExtraData {
    ExtraDataType getExtraDataType();

    void serialize(IOutgoingPacket packet);

    ILimitedData getLimitedData();

    void setLimitedData(ILimitedData data);

    void serializeState(IOutgoingPacket packet);

    String toJson();
}
