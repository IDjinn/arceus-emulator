package habbo.rooms;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomEntitiesComponent;
import habbo.rooms.components.gamemap.IGameMap;
import habbo.rooms.components.objects.IObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import packets.outgoing.rooms.RoomEntitiesComposer;
import packets.outgoing.rooms.RoomUserStatusComposer;
import packets.outgoing.rooms.prepare.*;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Room implements IRoom {
    private int id;
    private String name;
    private String password;
    private int maxUsers;

    @Inject
    private IObjectManager objectManager;
    @Inject
    private IGameMap gameMap;
    @Inject
    private IRoomEntitiesComponent entitiesComponent;

    // TODO: this is not the final solution, but we will use it for now. Just components must be able to inject tasks into this executor.
    private final Executor roomRunner = Executors.newVirtualThreadPerTaskExecutor();
    @Inject
    private IPathfinder pathfinder;

    @Inject
    RoomPrepareJoinPipeline joinPipeline;

    public Room(int roomId, String roomName) {
        this.id = roomId;
        this.name = roomName;
        this.maxUsers = 0;
        this.password = "";

        this.joinPipeline.process(new JoinEvent(null, null, null));
    }


    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public int getMaxUsers() {
        return 0;
    }

    @Override
    public void setMaxUsers(int maxUsers) {

    }

    @Override
    public int getMinUsers() {
        return 0;
    }

    @Override
    public void setMinUsers(int minUsers) {

    }

    @Override
    public boolean isPublic() {
        return false;
    }

    @Override
    public void setPublic(boolean isPublic) {

    }

    @Override
    public RoomAccess getRoomAccess() {
        return RoomAccess.Open;
    }

    @Override
    public void setRoomAccess(RoomAccess roomAccess) {

    }

    @Override
    public void init() {
        this.gameMap.init(this);
        this.entitiesComponent.init(this);
        this.pathfinder.init(this);
        this.objectManager.init(this);
        this.joinPipeline.init();
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
        return o.getId() - id;
    }


    @Override
    public void serialize(OutgoingPacket packet) {
        packet.appendInt(this.id);
        packet.appendString(this.name);
//        if (this.isPublic()) { TODO
        packet.appendInt(0);
        packet.appendString("");
//        } else {
//            packet.appendInt(this.ownerId);
//            packet.appendString(this.ownerName);
//        }
        packet.appendInt(this.getRoomAccess().getState());
        packet.appendInt(0);
        packet.appendInt(this.getMaxUsers());
        packet.appendString("this.description");
        packet.appendInt(0);
        packet.appendInt(0);//this.score
        packet.appendInt(0);
        packet.appendInt(0); // this.category

//        String[] tags = Arrays.stream(this.tags.split(";")).filter(t -> !t.isEmpty()).toArray(String[]::new);
//        packet.appendInt(tags.length);
//        for (String s : tags) {
//            packet.appendString(s);
//        }
        packet.appendInt(0);

        int base = 0;

//        if (this.getGuildId() > 0) {
//            base = base | 2;
//        }
//
//        if (this.isPromoted()) {
//            base = base | 4;
//        }
//
//        if (!this.isPublicRoom()) {
//            base = base | 8;
//        }


        packet.appendInt(base);


//        if (this.getGuildId() > 0) {
//            Guild g = Emulator.getGameEnvironment().getGuildManager().getGuild(this.getGuildId());
//            if (g != null) {
//                packet.appendInt(g.getId());
//                packet.appendString(g.getName());
//                packet.appendString(g.getBadge());
//            } else {
//                packet.appendInt(0);
//                packet.appendString("");
//                packet.appendString("");
//            }
//        }
//
//        if (this.promoted) {
//            packet.appendString(this.promotion.getTitle());
//            packet.appendString(this.promotion.getDescription());
//            packet.appendInt((this.promotion.getEndTimestamp() - Emulator.getIntUnixTimestamp()) / 60);
//        }

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
                new RoomModelComposer("model_a", getId()),
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
    public void schedule(Runnable runnable) {
        roomRunner.execute(runnable);
    }

    @Override
    public IPathfinder getPathfinder() {
        return this.pathfinder;
    }

    @Override
    public IObjectManager getObjectManager() {
        return this.objectManager;
    }
}
