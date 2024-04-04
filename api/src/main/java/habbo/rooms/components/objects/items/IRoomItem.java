package habbo.rooms.components.objects.items;

import habbo.furniture.IFurniture;
import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.IRoomObject;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;

import java.util.Optional;

public interface IRoomItem extends IRoomObject, ISerializable {
    long getId();

    IRoom getRoom();

    Optional<IHabboData> getOwnerData();

    void setOwnerData(IHabboData ownerData);

    int getGroup();

    IRoomItemData getItemData();

    IFurniture getFurniture();


    ILimitedData getLimitedData();


    String getExtraData();

    void setExtraData(String extraData);

    boolean isLimited();

    OutgoingPacket serializePosition(OutgoingPacket packet);

    OutgoingPacket serializeExtraData(OutgoingPacket packet);

    OutgoingPacket serializeLimitedData(OutgoingPacket packet);

    boolean canUse();
}
