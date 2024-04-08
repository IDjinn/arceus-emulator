package habbo.habbos.data;

import habbo.rooms.IRoom;

import java.util.List;

public interface IHabboRooms {
    void init();

    List<IRoom> getOwnRooms();

    List<IRoom> getFavoriteRooms();

    List<IRoom> getRoomHistory();

    List<IRoom> getRoomsWithRights();
}
