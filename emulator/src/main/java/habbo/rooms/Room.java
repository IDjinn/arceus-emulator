package habbo.rooms;

import com.google.inject.Inject;
import core.concurrency.IProcessHandler;
import core.concurrency.IThreadManager;
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
import org.jetbrains.annotations.Nullable;
import packets.outgoing.rooms.entities.RoomEntitiesComposer;
import packets.outgoing.rooms.entities.RoomUserStatusComposer;
import packets.outgoing.rooms.objects.floor.RoomFloorItemsComposer;
import packets.outgoing.rooms.objects.wall.RoomWallItemsComposer;
import packets.outgoing.rooms.prepare.*;
import storage.results.IConnectionResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Room implements IRoom {
    private final Logger logger = LogManager.getLogger();
    private final IRoomData data;

    private final Map<Class<? extends IRoomComponent>, IRoomComponent> customComponents;
    private IRoomModelData model;
    private boolean isFullyLoaded = false;
    
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
    @Inject
    private IProcessHandler processesHandler;


    public Room(IConnectionResult data) {
        this.data = new RoomData(data);
        this.customComponents = new ConcurrentHashMap<>();
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

        for (var customComponent : this.customComponents.values()) {
            customComponent.init(this);
        }

        this.onLoaded();
    }

    @Override
    public void update() {
        this.rightsManager.update();
        this.objectManager.update();
        this.entityManager.update();
        this.pathfinder.update();
        this.gameMap.update();

        for (var customComponent : this.customComponents.values()) {
            customComponent.update();
        }
    }

    @Override
    public void destroy() {
        this.rightsManager.destroy();
        this.objectManager.destroy();
        this.entityManager.destroy();
        this.pathfinder.destroy();
        this.gameMap.destroy();

        for (var customComponent : this.customComponents.values()) {
            customComponent.destroy();
        }

        this.processesHandler.unregisterAllProcess();
    }

    @Override
    public void onLoaded() {
        this.gameMap.onRoomLoaded();
        this.entityManager.onRoomLoaded();
        this.pathfinder.onRoomLoaded();
        this.objectManager.onRoomLoaded();
        this.rightsManager.onRoomLoaded();

        for (var customComponent : this.customComponents.values()) {
            customComponent.onRoomLoaded();
        }
        
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
        var entity = this.getEntityManager().createHabboEntity(habbo);

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
                new RoomRelativeMapComposer(this.getGameMap()),
                new RoomHeightMapComposer(this.getGameMap()),
                new RoomFloorItemsComposer(this.getObjectManager().getFurnitureOwners(), this.getObjectManager().getAllFloorItems()),
                new RoomWallItemsComposer(this.getObjectManager().getFurnitureOwners(), this.getObjectManager().getAllWallItems())
        );
    }

    @Override
    public void join(IHabbo habbo) {
        habbo.getClient().sendMessages(
                new RoomOpenComposer(),
                new HideDoorbellComposer(),
                new RoomRelativeMapComposer(this.getGameMap()),
                new RoomHeightMapComposer(this.getGameMap()),
                new RoomDataComposer(this, habbo, false, true),
                new RoomFloorItemsComposer(this.getObjectManager().getFurnitureOwners(), this.getObjectManager().getAllFloorItems()),
                new RoomWallItemsComposer(this.getObjectManager().getFurnitureOwners(), this.getObjectManager().getAllWallItems()),
                new OutgoingPacket(2402).appendInt(0)
        );

        this.broadcastMessages(
                new RoomEntitiesComposer(this.getEntityManager().getEntities()),
                new RoomUserStatusComposer(this.getEntityManager().getEntities())
        );
    }

    @Override
    public void broadcastMessage(OutgoingPacket packet) {
        for (var player : this.getEntityManager().getPlayers()) {
            player.getClient().sendMessage(packet);
        }
    }

    @Override
    public void broadcastMessages(OutgoingPacket... packets) {
        for (var player : this.getEntityManager().getPlayers()) {
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
    public IProcessHandler getProcessHandler() {
        return this.processesHandler;
    }

    @Nullable
    @Override
    public <TRoomComponent extends IRoomComponent> TRoomComponent getCustomComponent(final Class<TRoomComponent> componentType) {
        assert componentType != null;
        assert componentType.isInstance(this.customComponents.get(componentType));
        return componentType.cast(this.customComponents.get(componentType));
    }

    @Override
    public void registerCustomComponent(final Class<? extends IRoomComponent> component, IRoomComponent instance) {
        this.customComponents.put(component, instance);
        this.logger.debug("registered a custom room component for room {} with name {}", this.getData().getId(),
                instance.getClass().getName());   
    }
}
