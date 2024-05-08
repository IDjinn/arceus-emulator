package storage.repositories.messenger;

import storage.repositories.IConnectionRepository;
import storage.results.IConnectionResultConsumer;

public interface IMessengerRepository extends IConnectionRepository {
    void getAllFriendsOfHabbo(int habboId, IConnectionResultConsumer consumer);
}
