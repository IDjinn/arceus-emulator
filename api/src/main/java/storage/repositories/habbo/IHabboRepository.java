package storage.repositories.habbo;

import storage.results.IConnectionResultConsumer;

public interface IHabboRepository {
    void getHabboIdByAuthTicket(String authTicket, IConnectionResultConsumer consumer);

    void getHabboDataByAuthTicket(String authTicket, IConnectionResultConsumer consumer);
}
