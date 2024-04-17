package org.emulator.wireds.boxes.conditions;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import org.emulator.wireds.WiredEvent;

public abstract class WiredOrCondition extends WiredCondition {
    public WiredOrCondition(final IRoomItemData itemData, final IRoom room, final IFurniture furniture) {
        super(itemData, room, furniture);
    }

    public abstract boolean matches(final WiredEvent event);
}
