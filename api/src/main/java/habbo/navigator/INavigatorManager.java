package habbo.navigator;

import habbo.rooms.IRoom;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface INavigatorManager {
    public String validateView(@Nullable String view);

    public List<IRoom> getRoomsForView(String viewName, @Nullable String query);
}
