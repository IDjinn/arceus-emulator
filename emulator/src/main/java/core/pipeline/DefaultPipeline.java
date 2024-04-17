package core.pipeline;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultPipeline<Event extends PipelineEvent> implements IPipeline<Event> {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final List<Map.Entry<String, Step<Event>>> steps = new LinkedList<>();

    @Override
    public DefaultPipeline<Event> addAfter(String key, String afterKey, Step<Event> step) {
        logger.trace("adding step {} after {}", step, afterKey);
        for (int i = 0; i < this.steps.size(); i++) {
            if (this.steps.get(i).getKey().equals(afterKey)) {
                this.steps.add(i + 1, Map.entry(key, step));
                return this;
            }
        }
        return this;
    }

    @Override
    public DefaultPipeline<Event> addBefore(String key, String beforeKey, Step<Event> step) {
        logger.trace("adding step {} before {}", step, beforeKey);
        for (int i = 0; i < this.steps.size(); i++) {
            if (this.steps.get(i).getKey().equals(beforeKey)) {
                this.steps.add(i, Map.entry(key, step));
                return this;
            }
        }
        return this;
    }

    @Override
    public DefaultPipeline<Event> addStep(String key, Step<Event> step) {
        logger.trace("adding step {}", step);
        this.steps.addLast(Map.entry(key, step));
        return this;
    }

    @Override
    public DefaultPipeline<Event> addFirst(String key, Step<Event> step) {
        logger.trace("adding step {} at first", step);
        this.steps.addFirst(Map.entry(key, step));
        return this;
    }

    @Override
    public DefaultPipeline<Event> then(Runnable action) {
        this.addStep("end", context -> {
            if (!context.isFail())
                action.run();
            return context;
        });
        return this;
    }

    @Override
    public Optional<IPipelineContext<Event>> execute(Event event) {
        logger.trace("executing pipeline with event {}", event);
        final var context = new DefaultPipelineContext<>(event);
        for (var step : this.steps) {
            logger.trace("executing step {} for event {}", step.getKey(), event);
            step.getValue().execute(context);
            if (context.isFail()) {
                logger.warn("pipeline failed at step {} for event {}", step.getKey(), event);
                break;
            }

            logger.trace("pipeline finished step {} for event {}", step.getKey(), event);
        }
        logger.trace("pipeline finished for event {}", event);
        return Optional.of(context);
    }
}
