package habbo.rooms;

import core.IHotelService;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public interface IRoomManager extends IHotelService {
    public ConcurrentHashMap<Integer, IRoom> getLoadedRooms();

    public List<IRoom> getLoadedRoomsBy(Predicate<IRoom> predicate);

    public @Nullable IRoom tryLoadRoom(int roomId);
}
