package core.pipeline;

public interface Step<T extends PipelineEvent> {
    IPipelineContext<T> execute(IPipelineContext<T> context);
}
