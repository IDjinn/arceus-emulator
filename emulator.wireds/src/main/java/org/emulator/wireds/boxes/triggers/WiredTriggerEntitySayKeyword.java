package org.emulator.wireds.boxes.triggers;

import core.events.EventListener;
import core.events.EventListenerPriority;
import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.entities.events.RoomEntityTalkEvent;
import org.emulator.wireds.WiredEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WiredTriggerEntitySayKeyword extends WiredTrigger {
    public static final String InteractionName = "wf_trg_says_something";

    public WiredTriggerEntitySayKeyword(final IRoomItemData itemData, final IRoom room, final IFurniture furniture) {
        super(itemData, room, furniture);
    }


    @EventListener(priority = EventListenerPriority.Low)
    public void onEntityTalk(@NotNull RoomEntityTalkEvent entityTalkEvent) {
        final var result = this.executionPipeline.execute(
                new WiredEvent(
                        entityTalkEvent,
                        this.getPosition(),
                        Objects.hash(
                                entityTalkEvent.entity().getVirtualId(),
                                InteractionName.hashCode(),
                                this.getPosition()
                        )
                ).addTrigger(this)
        );
    }
}
