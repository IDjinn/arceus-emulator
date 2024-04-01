package habbohotel.navigator;

import habbohotel.rooms.IRoom;
import org.jetbrains.annotations.Nullable;

import java.util.SequencedCollection;

public interface INavigatorManager {
    public String validateView(@Nullable String view);

    public SequencedCollection<IRoom> getRoomsForView(String viewName, @Nullable String query);
}
