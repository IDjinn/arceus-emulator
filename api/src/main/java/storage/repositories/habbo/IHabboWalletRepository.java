package storage.repositories.habbo;

import storage.results.IConnectionResultConsumer;

public interface IHabboWalletRepository {
    void getHabboCurrencies(IConnectionResultConsumer consumer, int habboId);
}
