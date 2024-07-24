package habbo.habbos.data;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomFactory;
import habbo.rooms.IRoomManager;
import storage.repositories.habbo.IHabboRoomsRepository;
import utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class HabboRooms implements IHabboRooms {
    private final IHabbo habbo;

    @Inject
    private IHabboRoomsRepository repository;

    @Inject
    private IRoomManager roomManager;

    @Inject
    private IRoomFactory roomFactory;

    private final List<IRoom> ownRooms;

    private final List<IRoom> roomHistory;

    private final List<IRoom> favoriteRooms;

    private final List<IRoom> roomsWithRights;

    public HabboRooms(IHabbo habbo) {
        this.habbo = habbo;

        this.ownRooms = new ArrayList<>();
        this.roomHistory = new ArrayList<>();
        this.favoriteRooms = new ArrayList<>();
        this.roomsWithRights = new ArrayList<>();
    }

    public void init() {
        this.loadRooms();
        this.loadFavoriteRooms();
        this.loadRoomHistory();
        this.loadRoomWithRights();
    }

    private void loadRooms() {
        this.repository.loadRoomsForHabbo(result -> {
            if(result == null) return;

            IRoom room = this.roomManager.getRoomById(result.getInt("id"));

            if(room == null) {
                room = this.roomFactory.createRoom(result);
            }

            this.ownRooms.add(room);
        }, this.habbo.getData().getId());
    }

    private void loadFavoriteRooms() {
        this.repository.loadFavoriteRoomsForHabbo(result -> {
            if(result == null) return;

            IRoom room = this.roomManager.getRoomById(result.getInt("id"));

            if(room == null) {
                room = this.roomFactory.createRoom(result);
            }

            this.favoriteRooms.add(room);
        }, this.habbo.getData().getId());
    }

    private void loadRoomHistory() {
        this.repository.loadRoomHistoryForHabbo(result -> {
            if(result == null) return;

            IRoom room = this.roomManager.getRoomById(result.getInt("id"));

            if(room == null) {
                room = this.roomFactory.createRoom(result);
            }

            this.roomHistory.add(room);
        }, this.habbo.getData().getId(), TimeUtil.unixNow());

    }

    private void loadRoomWithRights() {
        this.repository.loadRoomsWithRightsForHabbo(result -> {
            if(result == null) return;

            IRoom room = this.roomManager.getRoomById(result.getInt("id"));

            if(room == null) {
                room = this.roomFactory.createRoom(result);
            }

            this.roomsWithRights.add(room);
        }, this.habbo.getData().getId());
    }

    @Override
    public List<IRoom> getOwnRooms() {
        return this.ownRooms;
    }

    @Override
    public List<IRoom> getFavoriteRooms() {
        return this.favoriteRooms;
    }

    @Override
    public List<IRoom> getRoomHistory() {
        return this.roomHistory;
    }

    @Override
    public List<IRoom> getRoomsWithRights() {
        return this.roomsWithRights;
    }


    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }
}
