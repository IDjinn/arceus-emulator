package habbo.rooms.components.objects.items.floor.interactions;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.floor.DefaultFloorItem;

public class LayFloorItem extends DefaultFloorItem {
    public static final String INTERACTION_NAME = "lay";

    public LayFloorItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);
    }
}
