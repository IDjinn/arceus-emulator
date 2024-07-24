package utils.interfaces;

import storage.results.IConnectionResult;

public interface IFillable {
    void fill(IConnectionResult result) throws Exception;
}
