package habbo.rooms.components.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.entities.HabboEntity;
import habbo.rooms.entities.IHabboEntity;
import habbo.rooms.entities.IRoomEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.rooms.RoomUserStatusComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoomEntitiesComponent implements IRoomEntitiesComponent {
    private Logger logger = LogManager.getLogger();
    private IRoom room;
    private final ConcurrentHashMap<Integer, IRoomEntity> entities;
    private final ConcurrentHashMap<Integer, IHabboEntity> players;

    public RoomEntitiesComponent() {
        entities = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();
    }

    @Override
    public IRoom getRoom() {
        return room;
    }

    @Override
    public void init(IRoom room) {
        this.room = room;
    }
    
    @Override
    public void onRoomLoaded() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public IHabboEntity createHabboEntity(IHabbo habbo) {
        var entity = new HabboEntity(habbo);
        entities.put(entity.getVirtualId(), entity);
        players.put(entity.getVirtualId(), entity);
        return entity;
    }

    @Override
    public List<IRoomEntity> getEntities() {
        return entities.values().stream().toList();
    }

    @Override
    public List<IHabboEntity> getPlayers() {
        return players.values().stream().toList();
    }

    @Override
    public void tick() {
        try {
            var entitiesUpdated = new ArrayList<IRoomEntity>(entities.size());
            for (var entity : entities.values()) {
                entity.tick();
                if (entity.isNeedUpdate()) {
                    entitiesUpdated.add(entity);
                    entity.setNeedUpdateStatus(false);
                }
            }

            if (!entitiesUpdated.isEmpty())
                this.getRoom().broadcastMessage(new RoomUserStatusComposer(entitiesUpdated));
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
