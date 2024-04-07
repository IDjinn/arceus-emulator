package habbo.habbos.data;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomFactory;
import habbo.rooms.IRoomManager;
import storage.repositories.habbo.IHabboRoomsRepository;

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

    private final List<IRoom> favoriteRooms;

    public HabboRooms(IHabbo habbo) {
        this.habbo = habbo;

        this.ownRooms = new ArrayList<>();
        this.favoriteRooms = new ArrayList<>();
    }

    public void init() {
        this.loadRooms();
        this.loadFavoriteRooms();
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

            IRoom room = this.roomManager.getRoomById(result.getInt("room_id"));

            if(room == null) {
                room = this.roomFactory.createRoom(result);
            }

            this.favoriteRooms.add(room);
        }, this.habbo.getData().getId());
    }

    public List<IRoom> getOwnRooms() {
        return this.ownRooms;
    }

    public List<IRoom> getFavoriteRooms() {
        return this.favoriteRooms;
    }


}
