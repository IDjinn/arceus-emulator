package storage.results;

public interface IConnectionResultConsumer {
    void accept(IConnectionResult result) throws Exception;
    void accept(boolean result) throws Exception;
}
