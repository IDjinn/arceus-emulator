package results;

import storage.results.IConnectionResult;

public interface ConnectionResultConsumer {
    void accept(IConnectionResult result) throws Exception;
}
