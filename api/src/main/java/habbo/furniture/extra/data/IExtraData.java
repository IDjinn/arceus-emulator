package habbo.furniture.extra.data;

import habbo.rooms.components.objects.items.ILimitedData;
import networking.packets.OutgoingPacket;

public interface IExtraData {
    public ExtraDataType getExtraDataType();

    public void serialize(OutgoingPacket packet);

    public ILimitedData getLimitedData();

    public void setLimitedData(ILimitedData data);


    public String toJson();
}
