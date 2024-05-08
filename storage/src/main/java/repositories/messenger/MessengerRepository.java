package repositories.messenger;

import com.google.inject.Singleton;
import queries.messenger.MessengerQuery;
import repositories.ConnectionRepository;
import storage.repositories.messenger.IMessengerRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class MessengerRepository extends ConnectionRepository implements IMessengerRepository {
    @Override
    public void getAllFriendsOfHabbo(final int habboId, final IConnectionResultConsumer consumer) {
        this.select(MessengerQuery.SELECT_ALL_FRIENDS_OF_HABBO.getQuery(), consumer, habboId);
    }
}
