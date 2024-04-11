package storage.repositories.habbo;

import habbo.habbos.data.IHabboData;
import habbo.habbos.data.IHabboSettings;
import storage.results.IConnectionBooleanResultConsumer;
import storage.results.IConnectionResultConsumer;

public interface IHabboRepository {
    void getHabboIdByAuthTicket(IConnectionResultConsumer consumer, String authTicket);
    void getHabboDataByAuthTicket(IConnectionResultConsumer consumer, String authTicket);

    void saveHabboData(IConnectionBooleanResultConsumer consumer, IHabboData habboData);

    void saveHabboSettings(IConnectionBooleanResultConsumer consumer, IHabboSettings habboSettings);
}
