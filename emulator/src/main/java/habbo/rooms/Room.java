package habbo.rooms;

import com.google.inject.Inject;
import core.IThreadManager;
import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomEntitiesComponent;
import habbo.rooms.components.gamemap.IGameMap;
import habbo.rooms.components.objects.IObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.data.IRoomData;
import habbo.rooms.data.RoomData;
import habbo.rooms.writers.RoomWriter;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import packets.outgoing.rooms.RoomEntitiesComposer;
import packets.outgoing.rooms.RoomUserStatusComposer;
import packets.outgoing.rooms.prepare.*;
import storage.results.IConnectionResult;
import utils.cycle.ICycle;

import java.util.concurrent.TimeUnit;

public class Room implements IRoom {
    @Inject
    private IObjectManager objectManager;

    @Inject
    private IGameMap gameMap;

    @Inject
    private IRoomEntitiesComponent entitiesComponent;

    @Inject
    private IThreadManager threadManager;

    @Inject
    private IPathfinder pathfinder;

    private final IRoomData data;

    private boolean isFullyLoaded = false;

    public Room(IConnectionResult data) {
        this.data = new RoomData(data);
    }

    public IRoomData getData() {
        return data;
    }

    @Override
    public void init() {
        this.gameMap.init(this);
        this.entitiesComponent.init(this);
        this.pathfinder.init(this);
        this.objectManager.init(this);

        threadManager.getSoftwareThreadExecutor().scheduleAtFixedRate(this.entitiesComponent::tick, 0, ICycle.DEFAULT_CYCLE_INTERVAL_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() {
        this.gameMap.destroy();
        this.entitiesComponent.destroy();
        this.pathfinder.destroy();
        this.objectManager.destroy();
    }

    @Override
    public void onLoaded() {
        this.gameMap.onRoomLoaded();
        this.entitiesComponent.onRoomLoaded();
        this.pathfinder.onRoomLoaded();
        this.objectManager.onRoomLoaded();
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
        var entity = getEntitiesComponent().createHabboEntity(habbo);

        habbo.setPlayerEntity(entity);

        habbo.getClient().sendMessages(
                new HideDoorbellComposer(),
                new RoomOpenComposer(),
                new RoomDataComposer(this, habbo, false, true),
                new RoomModelComposer("model_a", this.getData().getId()),
                new RoomPaintComposer("landscape", "0.0"),
                new RoomRightsComposer(0),
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
                new RoomEntitiesComposer(getEntitiesComponent().getEntities()),
                new RoomUserStatusComposer(getEntitiesComponent().getEntities())
        );
    }

    @Override
    public void broadcastMessage(OutgoingPacket packet) {
        for (var player : getEntitiesComponent().getPlayers()) {
            player.getClient().sendMessage(packet);
        }
    }

    @Override
    public void broadcastMessages(OutgoingPacket... packets) {
        for (var player : getEntitiesComponent().getPlayers()) {
            player.getClient().sendMessages(packets);
        }
    }

    public IRoomEntitiesComponent getEntitiesComponent() {
        return entitiesComponent;
    }

    public IGameMap getGameMap() {
        return gameMap;
    }

    @Override
    public IPathfinder getPathfinder() {
        return this.pathfinder;
    }

    @Override
    public IObjectManager getObjectManager() {
        return this.objectManager;
    }

    @Override
    public boolean isFullyLoaded() {
        return isFullyLoaded;
    }

    @Override
    public void setFullyLoaded(boolean isFullyLoaded) {
        this.isFullyLoaded = isFullyLoaded;
    }
}
