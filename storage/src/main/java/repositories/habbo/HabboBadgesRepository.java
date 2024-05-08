package repositories.habbo;

import com.google.inject.Singleton;
import queries.habbo.HabboBadgesQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboBadgesRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class HabboBadgesRepository extends ConnectionRepository implements IHabboBadgesRepository {

    @Override
    public void getAllBadges(final IConnectionResultConsumer consumer, final int habboId) {
        this.select(HabboBadgesQuery.GET_ALL_BADGES.get(), consumer, habboId);
    }

    @Override
    public void getEquippedBadges(final IConnectionResultConsumer consumer, final int habboId) {
        this.select(HabboBadgesQuery.GET_EQUIPPED_BADGES.get(), consumer, habboId);
    }
}
