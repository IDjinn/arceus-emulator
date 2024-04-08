package habbo.rooms;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.navigator.INavigatorManager;
import habbo.navigator.data.INavigatorPublicCategory;
import habbo.rooms.data.IRoomCategory;
import habbo.rooms.data.RoomCategory;
import habbo.rooms.data.models.IRoomModel;
import habbo.rooms.data.models.RoomModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.repositories.rooms.IRoomRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@Singleton
public class RoomManager implements IRoomManager {
    private final Logger logger = LogManager.getLogger();

    private final IRoomFactory roomFactory;

    private final IRoomRepository roomRepository;

    private final INavigatorManager navigatorManager;

    private final ConcurrentHashMap<Integer, IRoom> rooms;

    private final HashMap<Integer, IRoomCategory> roomCategories;

    private final HashMap<String, IRoomModel> roomModels;

    @Inject
    public RoomManager(
            IRoomFactory roomFactory,
            IRoomRepository roomRepository,
            INavigatorManager navigatorManager
    ) {
        this.roomFactory = roomFactory;
        this.roomRepository = roomRepository;
        this.navigatorManager = navigatorManager;

        this.rooms = new ConcurrentHashMap<>();

        this.roomModels = new HashMap<>();
        this.roomCategories = new HashMap<>();
    }

    @Override
    public @Nullable IRoom tryLoadRoom(int roomId) {
        return this.rooms.get(roomId);
    }

    @Override
    public void init() {
        this.loadPublicRooms();
        this.loadStaffPickedRooms();

        this.loadRoomCategories();
        this.loadRoomModels();
    }

    private void loadPublicRooms() {
        this.roomRepository.loadPublicRooms(result -> {
            if(result == null) return;

            try {
                final IRoom room = roomFactory.createRoom(result);

                this.rooms.put(room.getData().getId(), room);
            } catch (Exception e) {
                logger.error("Failed to load public room: {}", result.getInt("id"));
            }
        }, "1", "1");

        logger.info("Loaded all public rooms");
    }

    private void loadStaffPickedRooms() {
        this.roomRepository.loadStaffPickedRooms(result -> {
            if(result == null) return;

            try {
                final INavigatorPublicCategory category = this.navigatorManager.getPublicCategoryById(
                        result.getInt("public_cat_id")
                );

                if(category == null) return;

                IRoom room = this.getRoomById(result.getInt("room_id"));

                if(room != null) {
                    category.addRoom(room);
                    return;
                }

                room = roomFactory.createRoom(result);

                this.rooms.put(room.getData().getId(), room);
            } catch (Exception e) {
                logger.error("Failed to load staff picked room: {}", result.getInt("id"));
            }
        }, "1");

        logger.info("Loaded all staff picked rooms");
    }

    private void loadRoomCategories() {
        this.roomRepository.loadFlatCategories(result -> {
            if(result == null) return;

            final IRoomCategory roomCategory = new RoomCategory(result);

            this.roomCategories.put(roomCategory.getId(), roomCategory);
        });

        logger.info("Loaded {} room categories", this.roomCategories.size());
    }

    private void loadRoomModels() {
        this.roomRepository.loadRoomModels(result -> {
            if(result == null) return;

            final IRoomModel roomModel = new RoomModel(result);

            this.roomModels.put(roomModel.getData().getName(), roomModel);
        });

        logger.info("Loaded {} room models", this.roomModels.size());
    }

    @Override
    public ConcurrentHashMap<Integer, IRoom> getLoadedRooms() {
        return this.rooms;
    }

    @Override
    public HashMap<Integer, IRoomCategory> getRoomCategories() {
        return this.roomCategories;
    }

    public IRoomCategory getCategoryFromTab(String tabName) {
        return this.roomCategories.values().stream()
                .filter(category -> category.getCaptionSave().equalsIgnoreCase(tabName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public HashMap<String, IRoomModel> getRoomModels() {
        return this.roomModels;
    }

    @Override
    public List<IRoom> getLoadedRoomsBy(Predicate<IRoom> predicate) {
        var result = new ArrayList<IRoom>();

        for (var room : this.rooms.values()) {
            if (predicate.test(room))
                result.add(room);
        }

        Collections.sort(result);
        return result;
    }

    public IRoom getRoomById(int roomId) {
        return this.rooms.get(roomId);
    }

    public void addRoom(IRoom room) {
        this.rooms.putIfAbsent(room.getData().getId(), room);
    }

    @Override
    public void destroy() {
        this.rooms.values().forEach(IRoom::destroy);
    }
}
