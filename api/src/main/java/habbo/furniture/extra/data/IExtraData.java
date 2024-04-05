package habbo.furniture.extra.data;

import networking.packets.OutgoingPacket;

public interface IExtraData {
    public ExtraDataType getExtraDataType();

    public OutgoingPacket serialize(OutgoingPacket packet);

    public String toJson();
}
