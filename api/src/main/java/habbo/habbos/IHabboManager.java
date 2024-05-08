package habbo.habbos;

import habbo.habbos.data.IHabboData;

import java.util.Optional;

public interface IHabboManager {
    Optional<IHabbo> getOnlineHabboByUsername(final String name);

    Optional<IHabboData> getHabboDataByUsername(final String name);

    Optional<IHabboData> getHabboData(final int id);

    Optional<IHabbo> getOnlineHabbo(final int id);

    Optional<IHabbo> getOnlineHabbo(final String name);

    boolean isOnline(final String name);

    boolean isOnline(final int id);

    void cache(final IHabboData data);

    void invalidateCache(final int id);

    void onLogin(final IHabbo habbo);

    void onLogoff(final IHabbo habbo);
}
