package habbo.navigator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.habbos.IHabbo;
import habbo.navigator.tabs.NavigatorHabboTab;
import habbo.navigator.tabs.NavigatorOfficialTab;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            case "history_freq" -> habbo.getRooms().getRoomHistory();
            case "my_groups" -> new ArrayList<>(); // TODO: Implement guild rooms
            case "with_rights" -> habbo.getRooms().getRoomsWithRights();
            case "popular" -> this.getPopularRooms();
            case "categories" -> this.getPromotedRooms();
            case "with_friends" -> new ArrayList<>(); // TODO: Implement friends
            case "highest_score" -> this.getTopRatedRooms();
            default -> new ArrayList<>();
        };
    }

    public List<IRoom> getPublicRooms() {
        final List<IRoom> publicRooms = new ArrayList<>();

        this.roomManager.getLoadedRooms().forEach((_, room) -> {
            if(!room.getData().isPublic()) return;

            publicRooms.add(room);
        });

        // TODO: Sort

        return publicRooms;
    }

    public List<IRoom> getPopularRooms() {
        final List<IRoom> popularRooms = new ArrayList<>();

        this.roomManager.getLoadedRooms().forEach((_, room) -> {
            if(!room.isFullyLoaded()) return; // TODO: Check current users in room and if it's public

            popularRooms.add(room);
        });

        // TODO: Sort

        return popularRooms;
    }

    public List<IRoom> getPromotedRooms() {
        final List<IRoom> promotedRooms = new ArrayList<>();

        this.roomManager.getLoadedRooms().forEach((_, room) -> {
            if (!room.getData().isPromoted()) return;

            promotedRooms.add(room);
        });

        return promotedRooms;
    }

    public List<IRoom> getTopRatedRooms() {
        // TODO: Maybe cache this or improve this solution
        return this.roomManager.getLoadedRooms().values().stream()
                .filter(IRoom::isFullyLoaded)
                .sorted(Comparator.comparing(room -> room.getData().getScore()))
                .limit(20)
                .collect(Collectors.toList());
    }

}
