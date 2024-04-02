package habbo.navigator;

import com.google.inject.Inject;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomManager;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NavigatorManager implements INavigatorManager {
    private final IRoomManager roomManager;

    @Inject
    public NavigatorManager(IRoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public String validateView(@Nullable String view) {
        if (view == null)
            return "hotel_view";

        return view.trim();
    }

    @Override
    public List<IRoom> getRoomsForView(String viewName, @Nullable String query) {
        return roomManager.getLoadedRooms().values().stream().toList();
    }
}
