package habbo.rooms;

import com.google.inject.Inject;
import core.IThreadManager;
import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomEntityManager;
import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.objects.IRoomObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.components.rights.IRoomRightsManager;
import habbo.rooms.data.IRoomData;
import habbo.rooms.data.RoomData;
import habbo.rooms.data.models.IRoomModelData;
import habbo.rooms.writers.RoomWriter;
import networking.packets.OutgoingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import packets.outgoing.rooms.RoomEntitiesComposer;
import packets.outgoing.rooms.RoomUserStatusComposer;
import packets.outgoing.rooms.objects.floor.RoomFloorItemsComposer;
import packets.outgoing.rooms.objects.wall.RoomWallItemsComposer;
import packets.outgoing.rooms.prepare.*;
import storage.results.IConnectionResult;
import utils.ReflectionHelpers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Room implements IRoom {
    public ConcurrentHashMap<String, ScheduledFuture<?>> processes = new ConcurrentHashMap<>();
    @Inject
    private IRoomObjectManager objectManager;

    @Inject
    private IRoomGameMap gameMap;

    @Inject
    private IRoomEntityManager entityManager;
    @Inject
    private IThreadManager threadManager;

    @Inject
    private IPathfinder pathfinder;
    @Inject
    private IRoomRightsManager rightsManager;
    @Inject
    private IRoomManager roomManager;

    private final IRoomData data;
    private IRoomModelData model;

    private boolean isFullyLoaded = false;
    private final Logger logger = LogManager.getLogger();

    public Room(IConnectionResult data) {
        this.data = new RoomData(data);
    }

    public IRoomData getData() {
        return this.data;
    }

    @Override
    public void init() {
        this.model = this.roomManager.getRoomModels().get(this.getData().getModelName());
        
        this.gameMap.init(this);
        this.entityManager.init(this);
        this.pathfinder.init(this);
        this.objectManager.init(this);

    }

    @Override
    public void destroy() {
        this.gameMap.destroy();
        this.entityManager.destroy();
        this.pathfinder.destroy();
        this.objectManager.destroy();
        this.rightsManager.destroy();
        this.unregisterAllProcess();
    }

    @Override
    public void onLoaded() {
        this.gameMap.onRoomLoaded();
        this.entityManager.onRoomLoaded();
        this.pathfinder.onRoomLoaded();
        this.objectManager.onRoomLoaded();
        this.rightsManager.onRoomLoaded();
        this.setFullyLoaded(true);
    }

    @Override
    public int compareTo(@NotNull IRoom o) {
        return o.getData().getId() - this.getData().getId();
    }


    @Override
    public void write(OutgoingPacket packet) {
        RoomWriter.write(this, packet);
    }

    @Override
    public void prepareForHabbo(IHabbo habbo, String password) {
        // TODO: IN ROOM CHECKS
        habbo.setRoom(this);
        var entity = getEntityManager().createHabboEntity(habbo);

        habbo.setPlayerEntity(entity);

        habbo.getClient().sendMessages(
                new HideDoorbellComposer(),
                new RoomOpenComposer(),
                new RoomDataComposer(this, habbo, false, true),
                new RoomModelComposer(this.getData().getModelName(), this.getData().getId()),
                new RoomPaintComposer("landscape", "0.0"),
                new RoomRightsComposer(this.getRightsManager().getRightLevelFor(habbo)),
                new RoomScoreComposer(0, true),
                new RoomPromotionMessageComposer(),
                new RoomRelativeMapComposer(getGameMap()),
                new RoomHeightMapComposer(getGameMap()),
                new RoomFloorItemsComposer(this.getObjectManager().getFurnitureOwners(), this.getObjectManager().getAllFloorItems()),
                new RoomWallItemsComposer(this.getObjectManager().getFurnitureOwners(), this.getObjectManager().getAllWallItems())
        );
    }

    @Override
    public void join(IHabbo habbo) {
        habbo.getClient().sendMessages(
                new RoomOpenComposer(),
                new HideDoorbellComposer(),
                new RoomRelativeMapComposer(getGameMap()),
                new RoomHeightMapComposer(getGameMap()),
                new RoomDataComposer(this, habbo, false, true),
                new RoomFloorItemsComposer(this.getObjectManager().getFurnitureOwners(), this.getObjectManager().getAllFloorItems()),
                new RoomWallItemsComposer(this.getObjectManager().getFurnitureOwners(), this.getObjectManager().getAllWallItems()),
                new OutgoingPacket(2402).appendInt(0)
        );

        broadcastMessages(
                new RoomEntitiesComposer(getEntityManager().getEntities()),
                new RoomUserStatusComposer(getEntityManager().getEntities())
        );
    }

    @Override
    public void broadcastMessage(OutgoingPacket packet) {
        for (var player : getEntityManager().getPlayers()) {
            player.getClient().sendMessage(packet);
        }
    }

    @Override
    public void broadcastMessages(OutgoingPacket... packets) {
        for (var player : getEntityManager().getPlayers()) {
            player.getClient().sendMessages(packets);
        }
    }

    @Override
    public IRoomEntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public IRoomGameMap getGameMap() {
        return this.gameMap;
    }

    @Override
    public IPathfinder getPathfinder() {
        return this.pathfinder;
    }

    @Override
    public IRoomObjectManager getObjectManager() {
        return this.objectManager;
    }

    @Override
    public IRoomRightsManager getRightsManager() {
        return this.rightsManager;
    }

    @Override
    public IRoomModelData getModel() {
        return this.model;
    }

    @Override
    public boolean isFullyLoaded() {
        return this.isFullyLoaded;
    }

    @Override
    public void setFullyLoaded(boolean isFullyLoaded) {
        this.isFullyLoaded = isFullyLoaded;
    }

    @Override
    public void registerProcess(@NotNull String key, Runnable runnable, long interval, TimeUnit timeUnit) {
        if (this.processes.containsKey(key))
            throw new IllegalStateException("already registered");

        this.processes.put(key, this.threadManager.getSoftwareThreadExecutor().scheduleAtFixedRate(runnable
                , 0,
                interval,
                timeUnit));

        this.logger.debug("registered process '{}' for room '{}' by '{}'", key, this.getData().getId(),
                ReflectionHelpers.getCallerInfo());
    }

    @Override
    public boolean unregisterProcess(@NotNull String key) {
        var process = this.processes.get(key);
        if (process == null)
            return false;

        return process.cancel(true);
    }

    private void unregisterAllProcess() {
        for (var key : this.processes.keySet()) {
            try {
                unregisterProcess(key);
            } catch (Exception e) {
                this.logger.error("Unable to unregister process {} from room {} due exception {}", key, this.data.getId(), e.getMessage(), e);
            }
        }
    }
}
