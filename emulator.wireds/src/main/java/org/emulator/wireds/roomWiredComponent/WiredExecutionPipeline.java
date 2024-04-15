package org.emulator.wireds.roomWiredComponent;

import core.pipeline.DefaultPipeline;
import core.pipeline.IPipeline;
import org.emulator.wireds.WiredEvent;

public class WiredExecutionPipeline extends DefaultPipeline<WiredEvent> implements IPipeline<WiredEvent> {
    private final WiredManager wiredManager;

    public WiredExecutionPipeline(WiredManager wiredManager) {
        this.wiredManager = wiredManager;
    }

    public void init() {
        this.addStep("filter-triggers", ctx -> {
            final var trigger = this.wiredManager.getTriggers().size();
            return ctx;
        });
    }
}
