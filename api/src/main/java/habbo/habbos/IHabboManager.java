package habbo.habbos;

import habbo.habbos.data.IHabboData;

import java.util.Optional;

public interface IHabboManager {
    Optional<IHabbo> getOnlineHabboByUsername(String name);
    Optional<IHabboData> getHabboDataByUsername(String name);

    Optional<IHabboData> getHabboData(int id);

    void cache(IHabboData data);

    void invalidateCache(int id);

    void onLogin(IHabbo habbo);

    void onLogoff(IHabbo habbo);
}
