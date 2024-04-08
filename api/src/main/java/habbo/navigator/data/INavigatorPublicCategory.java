package habbo.navigator.data;

import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.rooms.IRoom;

import java.util.List;

public interface INavigatorPublicCategory {
    int getId();

    String getName();

    List<IRoom> getRooms();

    int getOrder();

    NavigatorDisplayMode getDisplayMode();

    void addRoom(IRoom room);

    void removeRoom(IRoom room);
}
