package habbo.habbos.data;

import habbo.habbos.IHabboComponent;
import habbo.rooms.IRoom;

import java.util.List;

public interface IHabboRooms extends IHabboComponent {
    void init();

    List<IRoom> getOwnRooms();

    List<IRoom> getFavoriteRooms();

    List<IRoom> getRoomHistory();

    List<IRoom> getRoomsWithRights();
}
