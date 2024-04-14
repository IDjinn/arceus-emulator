package core.pipeline;

public interface IPipeline<T extends PipelineEvent> {
    IPipeline<T> addAfter(String key, String afterKey, Step<T> step);

    IPipeline<T> addBefore(String key, String beforeKey, Step<T> step);

    IPipeline<T> addStep(String key, Step<T> step);

    IPipeline<T> addFirst(String key, Step<T> step);

    IPipeline<T> then(Runnable action);

    IPipelineContext<T> execute(T IPipelineObject);
}
