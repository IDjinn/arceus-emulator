package habbo.rooms.components.objects.items.floor.interactions;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.floor.DefaultFloorItem;

public class SitFloorItem extends DefaultFloorItem {
    public static final String INTERACTION_NAME = "sit";

    public SitFloorItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);
    }

}
