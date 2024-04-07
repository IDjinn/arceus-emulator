package habbo.rooms;

import core.IHotelService;
import habbo.rooms.data.IRoomCategory;
import habbo.rooms.data.models.IRoomModel;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public interface IRoomManager extends IHotelService {
    public ConcurrentHashMap<Integer, IRoom> getLoadedRooms();

    public List<IRoom> getLoadedRoomsBy(Predicate<IRoom> predicate);

    public @Nullable IRoom tryLoadRoom(int roomId);

    HashMap<Integer, IRoomCategory> getRoomCategories();

    HashMap<String, IRoomModel> getRoomModels();

    IRoom getRoomById(int roomId);
}
