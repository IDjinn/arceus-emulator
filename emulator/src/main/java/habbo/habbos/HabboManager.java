package habbo.habbos;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.IHotel;
import habbo.habbos.data.IHabboData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Singleton
public class HabboManager implements IHabboManager {
    private final Logger logger = LogManager.getLogger();
    private final IHotel hotel;
    private final Cache<Integer, IHabboData> habboDataCache;
    private final Cache<String, IHabboData> habboDataCacheByUsername;
    private final Map<Integer, IHabbo> connectedHabbos;

    @Inject
    public HabboManager(IHotel hotel) {
        this.hotel = hotel;

        this.habboDataCache = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
        this.habboDataCacheByUsername = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
        this.hotel.getProcessHandler().registerProcess(HabboManager.class.getSimpleName(), this::updateHabbos, 5, 5,
                TimeUnit.MINUTES);
        this.connectedHabbos = new ConcurrentHashMap<>();
    }

    private void updateHabbos() {
        for (var habbo : this.connectedHabbos.values()) {
            try {
                habbo.update();
            } catch (Exception e) {
                this.logger.error("error while updating habbo {}", habbo.getData().getId(), e);
            }
        }
    }

    @Override
    public Optional<IHabboData> getHabboDataByUsername(String name) {
        return Optional.ofNullable(this.habboDataCacheByUsername.getIfPresent(name));
    }

    public Optional<IHabboData> getHabboData(int id) {
        return Optional.ofNullable(this.habboDataCache.getIfPresent(id));
    }

    @Override
    public void cache(final IHabboData data) {
        this.habboDataCache.put(data.getId(), data);
        this.habboDataCacheByUsername.put(data.getUsername(), data);
    }

    @Override
    public void invalidateCache(final int id) {
        var cached = this.habboDataCache.getIfPresent(id);
        if (cached != null) this.habboDataCacheByUsername.invalidate(cached.getUsername());
        this.habboDataCache.invalidate(id);
    }

    @Override
    public void onLogin(final IHabbo habbo) {
        this.connectedHabbos.put(habbo.getData().getId(), habbo);
        this.cache(habbo.getData());
    }

    @Override
    public void onLogoff(final IHabbo habbo) {
        habbo.update();
        habbo.destroy();

        this.invalidateCache(habbo.getData().getId());
        this.connectedHabbos.remove(habbo.getData().getId());
    }
}
