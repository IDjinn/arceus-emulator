package habbo.rooms.components.entities;

import com.google.inject.Inject;
import com.google.inject.Injector;
import habbo.commands.ICommandManager;
import habbo.habbos.IHabbo;
import habbo.internationalization.IInternationalizationManager;
import habbo.internationalization.LocalizedString;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.PlayerEntity;
import habbo.rooms.entities.events.RoomEntityTalkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.rooms.entities.RoomUserStatusComposer;
import packets.outgoing.rooms.entities.chat.RoomUserShoutMessageComposer;
import packets.outgoing.rooms.entities.chat.RoomUserTalkMessageComposer;
import packets.outgoing.rooms.entities.chat.RoomUserWhisperMessageComposer;
import utils.cycle.ICycle;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomEntityManager implements IRoomEntityManager {
    private final Logger logger = LogManager.getLogger();
    private IRoom room;
    private final ConcurrentHashMap<Integer, IRoomEntity> entitiesByVirtualId;
    private final ConcurrentHashMap<Integer, IRoomEntity> entities;
    private final ConcurrentHashMap<Integer, IPlayerEntity> players;
    private final AtomicInteger virtualIdCounter;

    private final IInternationalizationManager internationalizationManager;
    private final ICommandManager commandManager;
    private final Injector injector;

    @Inject
    public RoomEntityManager(IInternationalizationManager internationalizationManager, ICommandManager commandManager, final Injector injector) {
        this.internationalizationManager = internationalizationManager;
        this.commandManager = commandManager;
        this.injector = injector;
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
        var entity = new PlayerEntity(habbo);
        this.injector.injectMembers(entity);
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
        this.getRoom().getProcessHandler().registerProcess(RoomEntityManager.class.getSimpleName(), this::tick,
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
    public void talk(final IRoomEntity entity, final String message, final int bubble) { // TODO MOVE THIS TO ENTITY*
        if (entity instanceof IPlayerEntity player && this.commandManager.isCommand(message)) {
            this.commandManager.execute(player.getHabbo(), message);
            return;
        }
        
        this.getRoom().getEventHandler().onEvent(new RoomEntityTalkEvent(entity, message, Timestamp.from(Instant.now())));
        this.getRoom().broadcastMessage(new RoomUserTalkMessageComposer(entity, message, 0, bubble));
    }

    @Override
    public void shout(final IRoomEntity entity, final String message, final int bubble) {
        this.getRoom().broadcastMessage(new RoomUserShoutMessageComposer(entity, message, 0, bubble));
    }

    @Override
    public void whisper(final IRoomEntity entity, final String message, final int bubble) {
        this.getRoom().broadcastMessage(new RoomUserWhisperMessageComposer(entity, message, 0, bubble));
    }

    @Override
    public void talk(final IRoomEntity entity, final LocalizedString message, final int bubble) { // TODO: THIS USE 
        // CLIENT LOCALE INSTEAD HARD-CODED ENGLISH
        this.getRoom().broadcastMessage(new RoomUserTalkMessageComposer(entity, this.internationalizationManager.getLocalizedString(message,
                Locale.ENGLISH), 0, bubble));
    }

    @Override
    public void shout(final IRoomEntity entity, final LocalizedString message, final int bubble) {
        this.getRoom().broadcastMessage(new RoomUserShoutMessageComposer(entity, this.internationalizationManager.getLocalizedString(message, Locale.ENGLISH), 0, bubble));
    }

    @Override
    public void whisper(final IRoomEntity entity, final LocalizedString message, final int bubble) {
        this.getRoom().broadcastMessage(new RoomUserWhisperMessageComposer(entity, this.internationalizationManager.getLocalizedString(message, Locale.ENGLISH), 0, bubble));
    }

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
