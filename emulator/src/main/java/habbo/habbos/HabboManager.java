package habbo.habbos;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.IHotel;
import habbo.habbos.data.IHabboData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.rooms.entities.RoomUserRemoveComposer;

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
    private final Map<String, IHabbo> connectedHabbosByUsername;

    @Inject
    public HabboManager(IHotel hotel) {
        this.hotel = hotel;
        this.connectedHabbos = new ConcurrentHashMap<>();
        this.connectedHabbosByUsername = new ConcurrentHashMap<>();

        this.hotel.getProcessHandler().registerProcess(HabboManager.class.getSimpleName(), this::updateHabbos, 5, 5,
                TimeUnit.MINUTES);
        this.habboDataCache = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
        this.habboDataCacheByUsername = Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
    }

    private void updateHabbos() {
        for (var habbo : this.connectedHabbos.values()) {
            try {
                habbo.update();
            } catch (Exception e) {
                this.logger.error("error while updating habbo {}", habbo.getData().getId(), e);
            }
        }

        this.logger.info("total of {} habbos updated", this.connectedHabbos.size());
    }

    @Override
    public Optional<IHabbo> getOnlineHabboByUsername(final String name) { // TODO IGNORE CASE, CULTURE ETC
        return Optional.ofNullable(this.connectedHabbosByUsername.get(name));
    }

    @Override
    public Optional<IHabboData> getHabboDataByUsername(String name) {
        return Optional.ofNullable(this.habboDataCacheByUsername.getIfPresent(name));
    }

    public Optional<IHabboData> getHabboData(int id) {
        return Optional.ofNullable(this.habboDataCache.getIfPresent(id));
    }

    @Override
    public Optional<IHabbo> getOnlineHabbo(final int id) {
        return Optional.ofNullable(this.connectedHabbos.get(id));
    }

    @Override
    public Optional<IHabbo> getOnlineHabbo(final String name) {
        return Optional.ofNullable(this.connectedHabbosByUsername.get(name));
    }

    @Override
    public boolean isOnline(final String name) {
        return this.connectedHabbosByUsername.containsKey(name);
    }

    @Override
    public boolean isOnline(final int id) {
        return this.connectedHabbos.containsKey(id);
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
        this.connectedHabbosByUsername.put(habbo.getData().getUsername(), habbo);
        this.cache(habbo.getData());
        habbo.onLoaded();
    }

    @Override
    public void onLogoff(final IHabbo habbo) {
        try {
            habbo.update();

            if (habbo.getRoom() != null && habbo.getPlayerEntity() != null) {
                habbo.getRoom().getEntityManager().kick(habbo.getPlayerEntity());
                habbo.getRoom().broadcastMessage(new RoomUserRemoveComposer(habbo.getPlayerEntity()));
            }
            
            habbo.destroy();
        } catch (Exception e) {
            this.logger.error("error while disposing habbo {}", habbo.getData().getId(), e);
        } finally {
            this.invalidateCache(habbo.getData().getId());
            this.connectedHabbos.remove(habbo.getData().getId());
            this.connectedHabbosByUsername.remove(habbo.getData().getUsername());
        }
    }
}
