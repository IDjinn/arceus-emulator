package org.emulator.wireds.boxes.triggers;

import core.events.EventListener;
import core.events.EventListenerPriority;
import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.entities.events.RoomEntityTalkEvent;
import org.emulator.wireds.WiredEvent;
import org.jetbrains.annotations.NotNull;

public class WiredTriggerEntitySayKeyword extends WiredTrigger {
    public static final String InteractionName = "wf_trg_says_something";

    public WiredTriggerEntitySayKeyword(final IRoomItemData itemData, final IRoom room, final IFurniture furniture) {
        super(itemData, room, furniture);
    }


    @EventListener(priority = EventListenerPriority.Low)
    public void onEntityTalk(@NotNull RoomEntityTalkEvent entityTalkEvent) {

        var message = entityTalkEvent.message();
        this.executionPipeline.execute(new WiredEvent());
    }
}
