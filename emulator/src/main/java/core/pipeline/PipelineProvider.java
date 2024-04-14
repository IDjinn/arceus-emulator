package core.pipeline;

import com.google.inject.Provider;

public class PipelineProvider<T extends PipelineEvent> implements Provider<IPipeline<T>> {
    @Override
    public IPipeline<T> get() {
        return new DefaultPipeline<T>();
    }
}
