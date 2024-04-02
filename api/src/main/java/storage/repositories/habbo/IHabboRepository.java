package storage.repositories.habbo;

import storage.results.IConnectionResult;

public interface IHabboRepository {
    public int getHabboIdByAuthTicket(String authTicket);

    public IConnectionResult getHabboDataByAuthTicket(String authTicket);
}
