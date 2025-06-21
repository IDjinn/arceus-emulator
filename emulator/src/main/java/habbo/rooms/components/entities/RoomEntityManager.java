package habbo.rooms.components.entities;

import com.google.inject.Inject;
import com.google.inject.Injector;
import core.ecs.IDomain;
import habbo.commands.ICommandManager;
import habbo.habbos.IHabbo;
import habbo.internationalization.IInternationalizationManager;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.PlayerEntity;
import habbo.rooms.entities.components.status.EntityStatusComponent;
import habbo.rooms.entities.components.status.UpdateEntityStatusSystem;
import habbo.rooms.entities.status.IEntityStatusComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.rooms.entities.RoomUserStatusComposer;
import utils.cycle.ICycle;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomEntityManager implements IRoomEntityManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<Integer, IRoomEntity> entitiesByVirtualId;
    private final Map<Integer, IRoomEntity> entities;
    private final Map<Integer, IPlayerEntity> players;
    private final AtomicInteger virtualIdCounter;
    private final IInternationalizationManager internationalizationManager;

    private IRoom room;
    private final ICommandManager commandManager;
    private final Injector injector;
    private final IDomain domain;

    @Inject
    public RoomEntityManager(IInternationalizationManager internationalizationManager, ICommandManager commandManager, final Injector injector, IDomain domain) {
        this.internationalizationManager = internationalizationManager;
        this.commandManager = commandManager;
        this.injector = injector;
        this.domain = domain;
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
    public IPlayerEntity createHabboEntity(IHabbo habbo) {

        var domainEntity = domain.getDominion().createEntity(habbo.getData().getUsername(),
            new EntityStatusComponent()
        );

       var systems = domain.getDominion()
                .createScheduler();

        systems.parallelSchedule(new UpdateEntityStatusSystem(domain));
        systems.tickAtFixedRate(2);

        var entity = new PlayerEntity(habbo);
        this.injector.injectMembers(entity);
        this.entities.put(entity.getVirtualId(), entity);
        this.players.put(entity.getVirtualId(), entity);
        entity.init();
        return entity;
    }

    @Override
    public void init(IRoom room) {
        this.room = room;
    }
    
    @Override
    public void onRoomLoaded() {
        this.getRoom().getProcessHandler().registerProcess(RoomEntityManager.class.getSimpleName(), this::tick, ICycle.DEFAULT_CYCLE_INTERVAL_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() {

    }

    @Override
    public List<IRoomEntity> getEntities() {
        return this.entities.values().stream().toList();
    }

    @Override
    public List<IPlayerEntity> getPlayers() {
        return this.players.values().stream().toList();
    }

    private final Collection<IRoomEntity> entitiesUpdated = new HashSet<>();

    @Override
    public void kick(final IRoomEntity roomEntity) {
        synchronized (this.entities) {
            this.entities.remove(roomEntity.getVirtualId());
            this.players.remove(roomEntity.getVirtualId());
        }
    }

    @Override
    public synchronized void tick() {
        try {
            this.entitiesUpdated.clear();
            for (var entity : this.entities.values()) {
                entity.tick();
                if (entity.getStatusComponent().isNeedUpdate()) {
                    this.entitiesUpdated.add(entity);
                    entity.getStatusComponent().setNeedUpdateStatus(false);
                }
            }

            if (!this.entitiesUpdated.isEmpty())
                this.getRoom().broadcastMessage(new RoomUserStatusComposer(this.entitiesUpdated));
        } catch (Exception e) {
            LOGGER.error("error while running tick entity manager for room {}", this.getRoom().getData().getId(), e);
        }
    }
}
