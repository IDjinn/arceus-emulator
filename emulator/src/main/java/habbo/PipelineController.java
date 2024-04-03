package habbo;

import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class PipelineController<TIn, TOut> {
    private final List<Map.Entry<String, Function<TIn, TOut>>> pipelines;

    public PipelineController() {
        this.pipelines = new LinkedList<>();
    }

    public abstract void init();

    public boolean add(String key, Function<TIn, TOut> pipeline) {
        return pipelines.add(new AbstractMap.SimpleEntry<>(key, pipeline));
    }

    public boolean addFirst(String key, Function<TIn, TOut> pipeline) {
        pipelines.addFirst(new AbstractMap.SimpleEntry<>(key, pipeline));
        return true;
    }

    public boolean remove(String key) {
        var index = -1;
        for (int i = 0; i < pipelines.size(); i++) {
            if (pipelines.get(i).getKey().equals(key)) {
                index = i;
                break;
            }
        }
        return pipelines.remove(index) != null;
    }

    public boolean addAfter(String name, Function<TIn, TOut> pipeline) {
        int afterIndex = -1;

        for (int i = 0; i < pipelines.size(); i++) {
            if (pipelines.get(i).getKey().equals(name)) {
                afterIndex = i;
                break;
            }
        }

        if (afterIndex == -1) return false;
        pipelines.add(afterIndex + 1, new AbstractMap.SimpleEntry<>(key, pipeline));
        return true;
    }

    public boolean addBefore(String key, Function<TIn, TOut> pipeline) {
        int beforeIndex = -1;
        for (int i = 0; i < pipelines.size(); i++) {
            if (pipelines.get(i).getKey().equals(key)) {
                beforeIndex = i;
                break;
            }
        }

        if (beforeIndex == 0) return addFirst(key, pipeline);
        if (beforeIndex > 0) {
            pipelines.add(beforeIndex - 1, new AbstractMap.SimpleEntry<>(key, pipeline));
            return true;
        }

        return false;
    }

    public @Nullable TOut process(TIn in) {
        TOut output = null;
        for (Map.Entry<String, Function<TIn, TOut>> pipeline : pipelines) {
            output = pipeline.getValue().apply(in);
            if (output == null) break;
        }

        return output;
    }
}
