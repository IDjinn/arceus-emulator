package habbo.rooms.components.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.entities.HabboEntity;
import habbo.rooms.entities.IHabboEntity;
import habbo.rooms.entities.IRoomEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.rooms.RoomUserStatusComposer;
import utils.cycle.ICycle;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomEntityManager implements IRoomEntityManager {
    private Logger logger = LogManager.getLogger();
    private IRoom room;
    private final ConcurrentHashMap<Integer, IRoomEntity> entitiesByVirtualId;
    private final ConcurrentHashMap<Integer, IRoomEntity> entities;
    private final ConcurrentHashMap<Integer, IHabboEntity> players;
    private final AtomicInteger virtualIdCounter;

    public RoomEntityManager() {
        this.entities = new ConcurrentHashMap<>();
        this.entitiesByVirtualId = new ConcurrentHashMap<>();
        this.players = new ConcurrentHashMap<>();
        this.virtualIdCounter = new AtomicInteger(0);
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public IHabboEntity createHabboEntity(IHabbo habbo) {
        var entity = new HabboEntity(habbo);

        this.entities.put(entity.getVirtualId(), entity);
        this.players.put(entity.getVirtualId(), entity);
        return entity;
    }

    @Override
    public void init(IRoom room) {
        this.room = room;
    }
    
    @Override
    public void onRoomLoaded() {
        this.getRoom().registerProcess(RoomEntityManager.class.getSimpleName(), this::tick,
                ICycle.DEFAULT_CYCLE_INTERVAL_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() {

    }

    @Override
    public List<IRoomEntity> getEntities() {
        return this.entities.values().stream().toList();
    }

    @Override
    public List<IHabboEntity> getPlayers() {
        return this.players.values().stream().toList();
    }

    private final Collection<IRoomEntity> entitiesUpdated = new HashSet<>();
    
    @Override
    public synchronized void tick() {
        try {
            this.entitiesUpdated.clear();
            for (var entity : this.entities.values()) {
                entity.tick();
                if (entity.isNeedUpdate()) {
                    this.entitiesUpdated.add(entity);
                    entity.setNeedUpdateStatus(false);
                }
            }

            if (!this.entitiesUpdated.isEmpty())
                this.getRoom().broadcastMessage(new RoomUserStatusComposer(this.entitiesUpdated));
        } catch (Exception e) {
            this.logger.error(e);
        }
    }
}
