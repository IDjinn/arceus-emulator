package org.emulator.wireds.boxes.triggers;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import org.emulator.wireds.boxes.WiredItem;

public abstract class WiredTrigger extends WiredItem {
    public WiredTrigger(final IRoomItemData itemData, final IRoom room, final IFurniture furniture) {
        super(itemData, room, furniture);
    }
}
