package core.pipeline;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DefaultPipeline<Event extends PipelineEvent> implements IPipeline<Event> {
    private final List<Map.Entry<String, Step<Event>>> steps = new LinkedList<>();

    @Override
    public DefaultPipeline<Event> addAfter(String key, String afterKey, Step<Event> step) {
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
        this.steps.addLast(Map.entry(key, step));
        return this;
    }

    @Override
    public DefaultPipeline<Event> addFirst(String key, Step<Event> step) {
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
    public DefaultPipelineContext<Event> execute(Event event) {
        final var context = new DefaultPipelineContext<>(event);
        for (var step : this.steps) {
            step.getValue().execute(context);
            if (context.isFail())
                break;
        }
        return context;
    }
}
