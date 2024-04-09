package habbo.rooms.components.objects.items;

import habbo.furniture.IFurniture;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.IRoomObject;
import habbo.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;

import java.util.Optional;

public interface IRoomItem extends IRoomObject {
    long getId();

    IRoom getRoom();

    Optional<IHabboData> getOwnerData();

    void setOwnerData(IHabboData ownerData);

    int getGroup();

    IRoomItemData getItemData();

    IFurniture getFurniture();


    IExtraData getExtraData();

    void setExtraData(IExtraData extraData);


    /**
     * write item id and sprite id to packet
     */
    public void serializeItemIdentity(OutgoingPacket packet);

    OutgoingPacket serializePosition(OutgoingPacket packet);



    boolean canUse();

    default boolean canInteract(IRoomEntity entity) {
        return this.canUse();
    }

    void sendUpdate();

    default void onInteract(IRoomEntity entity) {

    } 
}
