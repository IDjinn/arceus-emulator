package habbo.rooms;

import com.google.inject.Inject;
import core.IThreadManager;
import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomEntityManager;
import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.objects.IRoomObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import packets.outgoing.rooms.RoomEntitiesComposer;
import packets.outgoing.rooms.RoomUserStatusComposer;
import packets.outgoing.rooms.objects.RoomFloorItemsComposer;
import packets.outgoing.rooms.prepare.*;
import utils.cycle.ICycle;

import java.util.concurrent.TimeUnit;

public class Room implements IRoom {
    private int id;
    private String name;
    private String password;
    private int maxUsers;

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

    public Room(int roomId, String roomName) {
        this.id = roomId;
        this.name = roomName;
        this.maxUsers = 0;
        this.password = "";
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
        this.entityManager.init(this);
        this.pathfinder.init(this);
        this.objectManager.init(this);

        threadManager.getSoftwareThreadExecutor().scheduleAtFixedRate(this.entityManager::tick, 0, ICycle.DEFAULT_CYCLE_INTERVAL_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() {
        this.gameMap.destroy();
        this.entityManager.destroy();
        this.pathfinder.destroy();
        this.objectManager.destroy();
    }

    @Override
    public void onLoaded() {
        this.gameMap.onRoomLoaded();
        this.entityManager.onRoomLoaded();
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
        var entity = getEntityManager().createHabboEntity(habbo);

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

    public IRoomEntityManager getEntityManager() {
        return entityManager;
    }

    public IRoomGameMap getGameMap() {
        return gameMap;
    }

    @Override
    public IPathfinder getPathfinder() {
        return this.pathfinder;
    }

    @Override
    public IRoomObjectManager getObjectManager() {
        return this.objectManager;
    }
}
