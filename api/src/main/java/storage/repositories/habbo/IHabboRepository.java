package storage.repositories.habbo;

import storage.results.IConnectionResultConsumer;

public interface IHabboRepository {
    void getHabboIdByAuthTicket(IConnectionResultConsumer consumer, String authTicket);

    void getHabboDataByAuthTicket(IConnectionResultConsumer consumer, String authTicket);
}
