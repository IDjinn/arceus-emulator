package org.emulator.wireds;

import core.events.IEvent;
import core.pipeline.PipelineEvent;

public class WiredEvent extends PipelineEvent {
    private IEvent triggerEvent;

    public IEvent getTriggerEvent() {
        return triggerEvent;
    }
}
