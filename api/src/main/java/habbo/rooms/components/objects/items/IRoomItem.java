package habbo.rooms.components.objects.items;

import habbo.furniture.IFurniture;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.IHabbo;
import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.IRoomObject;
import habbo.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;

import java.util.Optional;

public interface IRoomItem extends IRoomObject {
    int getId();

    IRoom getRoom();

    Optional<IHabboData> getOwnerData();

    void setOwnerData(IHabboData ownerData);

    int getGroup();

    IRoomItemData getItemData();

    IFurniture getFurniture();


    IExtraData getExtraData();

    /**
     * write item id and sprite id to packet
     */
    void serializeItemIdentity(OutgoingPacket packet);

    OutgoingPacket serializePosition(OutgoingPacket packet);

    boolean needSave();

    void setNeedSave(boolean needSave);

    boolean canUse();

    default boolean canInteract(IRoomEntity entity) {
        return this.canUse();
    }

    void sendUpdate();

    /**
     * entity triggered interaction (click, double click etc)
     *
     * @param entity entity requesting interaction
     */
    default void onInteract(IRoomEntity entity) {

    }

    default void onRemove(IHabbo taker) {

    }
}
