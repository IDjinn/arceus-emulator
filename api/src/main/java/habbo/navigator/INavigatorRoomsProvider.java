package habbo.navigator;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.data.IRoomCategory;

import java.util.HashMap;
import java.util.List;

public interface INavigatorRoomsProvider {
    List<IRoom> getRoomFromCategory(String category, IHabbo habbo);

    List<IRoom> getPublicRooms();

    List<IRoom> getPopularRooms();

    List<IRoom> getPromotedRooms();

    HashMap<IRoomCategory, List<IRoom>> getRoomsFromCategories(IHabbo habbo);
}
