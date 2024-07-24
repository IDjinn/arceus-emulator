package core.pipeline;

public class DefaultPipelineContext<T extends PipelineEvent> implements IPipelineContext<T> {
    private final T event;
    private boolean fail;

    public DefaultPipelineContext(T event) {
        this.event = event;
    }

    @Override
    public DefaultPipelineContext<T> fail(boolean cancelled) {
        this.fail = cancelled;
        return this;
    }

    @Override
    public boolean isFail() {
        return this.fail;
    }

    @Override
    public T getEvent() {
        return this.event;
    }
}
