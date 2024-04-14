package core.pipeline;

public interface IPipelineContext<T extends PipelineEvent> {
    IPipelineContext<T> fail(boolean cancelled);

    boolean isFail();

    T getEvent();
}
