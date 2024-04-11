package habbo.rooms;

import core.IHotelService;
import habbo.rooms.data.IRoomCategory;
import habbo.rooms.data.models.IRoomModelData;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public interface IRoomManager extends IHotelService {
    ConcurrentHashMap<Integer, IRoom> getLoadedRooms();

    List<IRoom> getLoadedRoomsBy(Predicate<IRoom> predicate);

    @Nullable IRoom tryLoadRoom(int roomId);

    HashMap<Integer, IRoomCategory> getRoomCategories();

    IRoomCategory getCategoryFromTab(String tabName);

    HashMap<String, IRoomModelData> getRoomModels();

    IRoom getRoomById(int roomId);

    void addRoom(IRoom room);
}
