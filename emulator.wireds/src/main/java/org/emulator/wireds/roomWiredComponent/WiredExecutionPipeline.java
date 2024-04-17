package org.emulator.wireds.roomWiredComponent;

import com.google.inject.Inject;
import core.concurrency.IThreadManager;
import core.pipeline.DefaultPipeline;
import core.pipeline.IPipeline;
import core.pipeline.IPipelineContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.emulator.wireds.WiredEvent;
import org.emulator.wireds.boxes.conditions.LogicalType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class WiredExecutionPipeline extends DefaultPipeline<WiredEvent> implements IPipeline<WiredEvent> {
    private static final Logger logger = LogManager.getLogger();
    private final WiredManager wiredManager;
    private final Map<Integer, Future<Optional<IPipelineContext<WiredEvent>>>> executingStacks;
    private @Inject IThreadManager threadManager;

    public WiredExecutionPipeline(WiredManager wiredManager) {
        this.wiredManager = wiredManager;
        this.executingStacks = new ConcurrentHashMap<>();
        this.addStep("handle-conditions", ctx -> {
            final var conditions = this.wiredManager.getConditionsAt(ctx.getEvent().getTriggerPosition());
            final var conditionsOrIter = conditions.stream().filter(condition -> condition.getLogicalType().equals(LogicalType.OR)).iterator();
            while (conditionsOrIter.hasNext()) {
                final var condition = conditionsOrIter.next();
                if (condition.matches(ctx.getEvent())) {
                    ctx.getEvent().addCondition(condition);
                    conditionsOrIter.remove();
                    break;
                }
                conditionsOrIter.remove();
            }

            for (final var condition : conditions) {
                if (!condition.matches(ctx.getEvent())) {
                    ctx.fail(true);
                    break;
                }
                ctx.getEvent().addCondition(condition);
            }

            return ctx;
        });
        this.addStep("handle-selectors", ctx -> ctx);
        this.addStep("get-effect-list", ctx -> {
            final var effects = this.wiredManager.getEffectsAt(ctx.getEvent().getTriggerPosition());
            ctx.getEvent().addEffects(effects);
            return ctx;
        });
        this.addStep("handle-addons", ctx -> {
            // unique, random etc...
            return ctx;
        });
        this.addStep("handle-effects", ctx -> {
            for (final var effect : ctx.getEvent().getEffects()) {
                effect.evaluate(ctx.getEvent());
            }
            return ctx;
        });
    }

    @Override
    public Optional<IPipelineContext<WiredEvent>> execute(final WiredEvent event) {
        if (this.executingStacks.containsKey(event.hashCode())) {
            logger.trace("wireds is already executing: {} {}",
                    event.getTriggers().getFirst().getClass().getSimpleName(),
                    event
            );
            return Optional.empty();
        }

        try {
            final var future = this.threadManager.getSoftwareThreadExecutor().submit(() -> super.execute(event));
            this.executingStacks.put(event.hashCode(), future);
            return future.get();
        } catch (Exception e) {
            logger.error("error while executing wireds: {}", e.getMessage(), e);
            return Optional.empty();
        } finally {
            this.executingStacks.remove(event.hashCode());
        }
    }
}
