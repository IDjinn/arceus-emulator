package habbo.rooms;

import core.IHotelService;
import habbo.rooms.data.IRoomCategory;
import habbo.rooms.data.IRoomModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface IRoomManager extends IHotelService {
    Map<Integer, IRoom> getLoadedRooms();

    List<IRoom> getLoadedRoomsBy(Predicate<IRoom> predicate);

    @Nullable IRoom tryLoadRoom(int roomId);

    Map<Integer, IRoomCategory> getRoomCategories();

    IRoomCategory getCategoryFromTab(String tabName);

    Map<String, IRoomModelData> getRoomModels();

    IRoom getRoomById(int roomId);

    void addRoom(IRoom room);
}
