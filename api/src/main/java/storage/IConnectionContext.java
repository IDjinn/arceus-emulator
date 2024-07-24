package storage;

import storage.providers.IConnectionProvider;

public interface IConnectionContext {
    IConnectionProvider getProvider();
}
