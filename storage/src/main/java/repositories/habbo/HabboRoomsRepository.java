package repositories.habbo;

import com.google.inject.Singleton;
import queries.habbo.HabboRoomQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboRoomsRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class HabboRoomsRepository extends ConnectionRepository implements IHabboRoomsRepository {
    public void loadRoomsForHabbo(IConnectionResultConsumer consumer, int habboId) {
        this.select(HabboRoomQuery.SELECT_ALL_ROOMS.get(), consumer, habboId);
    }

    public void loadFavoriteRoomsForHabbo(IConnectionResultConsumer consumer, int habboId) {
        this.select(HabboRoomQuery.SELECT_ALL_FAVORITE_ROOMS.get(), consumer, habboId);
    }
}
