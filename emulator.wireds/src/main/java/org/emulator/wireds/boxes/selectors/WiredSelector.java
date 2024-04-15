package org.emulator.wireds.boxes.selectors;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import org.emulator.wireds.boxes.WiredItem;

public class WiredSelector extends WiredItem {
    public WiredSelector(final IRoomItemData itemData, final IRoom room, final IFurniture furniture) {
        super(itemData, room, furniture);
    }
}
