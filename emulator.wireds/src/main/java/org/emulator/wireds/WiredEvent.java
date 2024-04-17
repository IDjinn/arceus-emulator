package org.emulator.wireds;

import core.events.IEvent;
import core.pipeline.PipelineEvent;
import org.emulator.wireds.boxes.conditions.WiredCondition;
import org.emulator.wireds.boxes.effects.WiredEffect;
import org.emulator.wireds.boxes.selectors.WiredSelector;
import org.emulator.wireds.boxes.triggers.WiredTrigger;
import utils.pathfinder.Position;

import java.util.LinkedList;
import java.util.List;

public class WiredEvent extends PipelineEvent {
    private final IEvent triggerEvent;
    private final List<WiredTrigger> triggers;
    private final List<WiredSelector> selectors;
    private final List<WiredCondition> conditions;
    private final List<WiredEffect> effects;
    private final int hash;
    private final Position triggerPosition;

    public WiredEvent(IEvent triggerEvent, Position triggerPosition, int hash) {
        this.triggerEvent = triggerEvent;
        this.triggerPosition = triggerPosition;
        this.hash = hash;
        this.triggers = new LinkedList<>();
        this.selectors = new LinkedList<>();
        this.conditions = new LinkedList<>();
        this.effects = new LinkedList<>();
    }

    public IEvent getTriggerEvent() {
        return this.triggerEvent;
    }

    public List<WiredTrigger> getTriggers() {
        return this.triggers;
    }

    public List<WiredSelector> getSelectors() {
        return this.selectors;
    }

    public List<WiredCondition> getConditions() {
        return this.conditions;
    }

    public List<WiredEffect> getEffects() {
        return this.effects;
    }

    public WiredEvent addTrigger(WiredTrigger trigger) {
        this.triggers.add(trigger);
        return this;
    }

    public WiredEvent addSelector(WiredSelector selector) {
        this.selectors.add(selector);
        return this;
    }

    public WiredEvent addCondition(WiredCondition condition) {
        this.conditions.add(condition);
        return this;
    }

    public WiredEvent addEffect(WiredEffect effect) {
        this.effects.add(effect);
        return this;
    }

    public Position getTriggerPosition() {
        return this.triggerPosition;
    }


    @Override
    public int hashCode() {
        return this.hash;
    }

    public void addEffects(final List<WiredEffect> effects) {
        this.effects.addAll(effects);
    }
}
