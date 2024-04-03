package core;

public interface IPipeline<TIn, TOut> {
    public String getName();

    public TOut process(TIn input);
}
