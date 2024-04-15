package org.emulator.wireds.boxes.conditions;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import org.emulator.wireds.boxes.WiredItem;

public class WiredCondition extends WiredItem {
    public WiredCondition(final IRoomItemData itemData, final IRoom room, final IFurniture furniture) {
        super(itemData, room, furniture);
    }
}
