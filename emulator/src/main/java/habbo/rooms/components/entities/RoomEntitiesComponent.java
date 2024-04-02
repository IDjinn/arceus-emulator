package habbo.rooms.components.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.PlayerEntity;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoomEntitiesComponent implements IRoomEntitiesComponent {
    private final IRoom room;
    private final ConcurrentHashMap<Integer, IRoomEntity> entities;
    private final ConcurrentHashMap<Integer, IPlayerEntity> players;

    public RoomEntitiesComponent(IRoom room) {
        this.room = room;
        entities = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();
    }

    @Override
    public IRoom getRoom() {
        return room;
    }

    @Override
    public void init() {

    }

    @Override
    public void onRoomLoaded() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public IPlayerEntity createHabboEntity(IHabbo habbo) {
        var entity = new PlayerEntity(habbo);
        entities.put(entity.getVirtualId(), entity);
        players.put(entity.getVirtualId(), entity);
        return entity;
    }

    @Override
    public List<IRoomEntity> getEntities() {
        return entities.values().stream().toList();
    }

    @Override
    public List<IPlayerEntity> getPlayers() {
        return players.values().stream().toList();
    }
}
