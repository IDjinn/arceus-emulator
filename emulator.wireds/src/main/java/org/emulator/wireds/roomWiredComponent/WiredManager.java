package org.emulator.wireds.roomWiredComponent;

import com.google.inject.Inject;
import com.google.inject.Injector;
import core.events.IEvent;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomComponent;
import org.emulator.wireds.boxes.WiredItem;
import org.emulator.wireds.boxes.addons.WiredAddon;
import org.emulator.wireds.boxes.conditions.WiredCondition;
import org.emulator.wireds.boxes.effects.WiredEffect;
import org.emulator.wireds.boxes.selectors.WiredSelector;
import org.emulator.wireds.boxes.triggers.WiredTrigger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WiredManager implements IRoomComponent {
    private final IRoom room;
    private final WiredExecutionPipeline executionPipeline;
    private final Map<Integer, WiredItem> wireds;
    private final Map<Integer, WiredTrigger> triggers;
    private final Map<Integer, WiredCondition> conditions;
    private final Map<Integer, WiredEffect> effects;
    private final Map<Integer, WiredAddon> addons;
    private final Map<Integer, WiredSelector> selectors;
    @Inject
    private Injector injector;

    public WiredManager(IRoom room) {
        this.room = room;
        this.wireds = new ConcurrentHashMap<>();
        this.triggers = new ConcurrentHashMap<>();
        this.conditions = new ConcurrentHashMap<>();
        this.effects = new ConcurrentHashMap<>();
        this.addons = new ConcurrentHashMap<>();
        this.selectors = new ConcurrentHashMap<>();
        this.executionPipeline = new WiredExecutionPipeline(this);
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public void init(final IRoom room) {
    }

    @Override
    public void onRoomLoaded() {
        for (final var floorItem : this.getRoom().getObjectManager().getAllFloorItems()) {
            if (!(floorItem instanceof WiredItem wiredItem)) continue;

            this.registerWired(wiredItem);
        }
        ;
    }

    @Override
    public void destroy() {

    }

    public void registerWired(WiredItem wiredItem) {
        this.injector.injectMembers(wiredItem);
        this.getRoom().getEventHandler().subscribe(wiredItem);
        synchronized (this.wireds) {
            this.wireds.put(wiredItem.getId(), wiredItem);
            switch (wiredItem) {
                case WiredAddon addon -> this.addons.put(addon.getId(), addon);
                case WiredTrigger trigger -> this.triggers.put(trigger.getId(), trigger);
                case WiredEffect effect -> this.effects.put(effect.getId(), effect);
                case WiredCondition condition -> this.conditions.put(condition.getId(), condition);
                case WiredSelector selector -> this.selectors.put(selector.getId(), selector);
                default -> throw new IllegalStateException("Invalid wired type");
            }
        }
    }

    public Map<Integer, WiredItem> getWireds() {
        return this.wireds;
    }

    public Map<Integer, WiredTrigger> getTriggers() {
        return this.triggers;
    }

    public Map<Integer, WiredTrigger> getTriggersForEvent(IEvent event) {
        return this.triggers;
    }

    public Map<Integer, WiredCondition> getConditions() {
        return this.conditions;
    }

    public Map<Integer, WiredEffect> getEffects() {
        return this.effects;
    }

    public Map<Integer, WiredAddon> getAddons() {
        return this.addons;
    }

    public Map<Integer, WiredSelector> getSelectors() {
        return this.selectors;
    }

    public WiredExecutionPipeline getExecutionPipeline() {
        return executionPipeline;
    }
}
