package org.emulator.wireds.boxes.effects;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import org.emulator.wireds.WiredEvent;
import org.emulator.wireds.boxes.WiredItem;

public abstract class WiredEffect extends WiredItem {
    public WiredEffect(final IRoomItemData itemData, final IRoom room, final IFurniture furniture) {
        super(itemData, room, furniture);
    }

    public abstract boolean evaluate(final WiredEvent event);
}
