package storage.repositories.habbo;

import storage.results.IConnectionResultConsumer;

public interface IHabboBadgesRepository {
    void getAllBadges(IConnectionResultConsumer consumer, int habboId);

    void getEquippedBadges(IConnectionResultConsumer consumer, int habboId);
}
