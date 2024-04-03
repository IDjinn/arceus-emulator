package habbo;

import core.IPipeline;
import core.IPipelineController;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public abstract class PipelineController<TIn, TOut> implements IPipelineController<TIn, TOut> {
    private final List<IPipeline<TIn, TOut>> pipelines;

    public PipelineController() {
        this.pipelines = new LinkedList<>();
    }

    public abstract void init();

    @Override
    public boolean addLast(IPipeline<TIn, TOut> pipeline) {
        return pipelines.add(pipeline);
    }

    @Override
    public boolean addFirst(IPipeline<TIn, TOut> pipeline) {
        pipelines.addFirst(pipeline);
        return true;
    }

    @Override
    public boolean remove(IPipeline<TIn, TOut> pipeline) {
        return pipelines.remove(pipeline);
    }

    @Override
    public boolean addAfter(String name, IPipeline<TIn, TOut> pipeline) {
        int afterIndex = -1;

        for (int i = 0; i < pipelines.size(); i++) {
            if (pipelines.get(i).getName().equals(name)) {
                afterIndex = i;
                break;
            }
        }

        if (afterIndex == -1) return false;
        pipelines.add(afterIndex + 1, pipeline);
        return true;
    }

    @Override
    public boolean addBefore(String name, IPipeline<TIn, TOut> pipeline) {
        int beforeIndex = -1;
        for (int i = 0; i < pipelines.size(); i++) {
            if (pipelines.get(i).getName().equals(name)) {
                beforeIndex = i;
                break;
            }
        }

        if (beforeIndex == 0) return addFirst(pipeline);
        if (beforeIndex > 0) {
            pipelines.add(beforeIndex - 1, pipeline);
            return true;
        }

        return false;
    }

    @Override
    public @Nullable TOut process(TIn in) {
        TOut output = null;
        for (IPipeline<TIn, TOut> pipeline : pipelines) {
            output = pipeline.process(in);
            if (output == null) break;
        }

        return output;
    }
}
