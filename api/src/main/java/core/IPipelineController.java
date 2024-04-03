package core;

import org.jetbrains.annotations.Nullable;


public interface IPipelineController<TIn, TOut> {

    public boolean addLast(IPipeline<TIn, TOut> pipeline);

    public boolean addFirst(IPipeline<TIn, TOut> pipeline);

    public boolean addAfter(String name, IPipeline<TIn, TOut> pipeline);

    public boolean addBefore(String name, IPipeline<TIn, TOut> pipeline);

    public boolean remove(IPipeline<TIn, TOut> pipeline);

    public @Nullable TOut process(TIn in);
}