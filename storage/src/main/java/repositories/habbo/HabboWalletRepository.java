package repositories.habbo;

import com.google.inject.Singleton;
import queries.habbo.HabboWalletQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboWalletRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class HabboWalletRepository extends ConnectionRepository implements IHabboWalletRepository {
    @Override
    public void getHabboCurrencies(final IConnectionResultConsumer consumer, final int habboId) {
        this.select(HabboWalletQuery.GET_ALL_CURRENCIES.get(), consumer, habboId);
    }
}
