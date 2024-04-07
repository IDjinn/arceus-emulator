package storage.repositories.habbo;

import storage.results.IConnectionResultConsumer;

public interface IHabboRoomsRepository {
    void loadRoomsForHabbo(IConnectionResultConsumer consumer, int habboId);

    void loadFavoriteRoomsForHabbo(IConnectionResultConsumer consumer, int habboId);

    void loadRoomHistoryForHabbo(IConnectionResultConsumer consumer, int habboId, long timestamp);

    void loadRoomsWithRightsForHabbo(IConnectionResultConsumer consumer, int habboId);
}
