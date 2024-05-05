package habbo.furniture.extra.data;

import habbo.rooms.components.objects.items.ILimitedData;
import networking.packets.OutgoingPacket;

public interface IExtraData {
    ExtraDataType getExtraDataType();

    void serialize(OutgoingPacket packet);

    ILimitedData getLimitedData();

    void setLimitedData(ILimitedData data);
    void serializeState(OutgoingPacket packet);

    String toJson();


    int getState();

    void setState(final int state);
}
