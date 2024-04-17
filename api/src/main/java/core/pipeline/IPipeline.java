package core.pipeline;

import java.util.Optional;

public interface IPipeline<T extends PipelineEvent> {
    IPipeline<T> addAfter(String key, String afterKey, Step<T> step);

    IPipeline<T> addBefore(String key, String beforeKey, Step<T> step);

    IPipeline<T> addStep(String key, Step<T> step);

    IPipeline<T> addFirst(String key, Step<T> step);

    IPipeline<T> then(Runnable action);

    Optional<IPipelineContext<T>> execute(T IPipelineObject);
}
