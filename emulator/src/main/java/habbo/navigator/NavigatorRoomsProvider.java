package habbo.navigator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.habbos.IHabbo;
import habbo.navigator.tabs.NavigatorHabboTab;
import habbo.navigator.tabs.NavigatorOfficialTab;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomManager;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class NavigatorRoomsProvider implements INavigatorRoomsProvider {
    @Inject
    private INavigatorManager navigatorManager;

    @Inject
    private IRoomManager roomManager;

    public List<IRoom> getRoomFromCategory(String category, IHabbo habbo) {
        return switch (category) {
            case NavigatorOfficialTab.FILTER_NAME -> this.getPublicRooms();
            case "my" -> habbo.getRooms().getOwnRooms();
            case "favorites" -> habbo.getRooms().getFavoriteRooms();
            default -> new ArrayList<>();
        };
    }

    public List<IRoom> getPublicRooms() {
        final List<IRoom> publicRooms = new ArrayList<>();

        this.roomManager.getLoadedRooms().forEach((_, room) -> {
            if(!room.isPublic()) return;

            publicRooms.add(room);
        });

        return publicRooms;
    }

}
