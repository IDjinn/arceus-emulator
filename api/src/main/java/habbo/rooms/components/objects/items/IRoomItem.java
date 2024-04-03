package habbo.rooms.components.objects.items;

import habbo.furniture.IFurniture;
import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.IRoomObject;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;

public interface IRoomItem extends IRoomObject, ISerializable {
    public long getId();

    public IRoom getRoom();

    public IHabboData getOwnerData();

    public int getGroup();

    public IRoomItemData getItemData();

    public IFurniture getFurniture();

    public ILimitedData getLimitedData();

    public boolean isLimited();

    public OutgoingPacket serializePosition(OutgoingPacket packet);

    public OutgoingPacket serializeExtraData(OutgoingPacket packet);

    public OutgoingPacket serializeLimitedData(OutgoingPacket packet);
    
}
