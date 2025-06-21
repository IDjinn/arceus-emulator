package habbo.rooms.components.objects.items.floor.interactions;

import com.google.inject.Inject;
import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.floor.DefaultFloorItem;
import habbo.rooms.entities.IRoomEntity;
import lombok.Getter;
import storage.repositories.rooms.IRoomItemsRepository;

public class DoorTeleportItem extends DefaultFloorItem {
    public static final String INTERACTION_NAME = "teleport";
    private static final int PairNotFound = -100;
    private static final int InvalidPair = -1;
    @Inject
    IRoomItemsRepository itemsRepository;

    @Getter
    private int pairId = InvalidPair;

    public DoorTeleportItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);
    }

    private boolean isValidTeleport() {
        if (this.getPairId() == InvalidPair) {
            this.itemsRepository.getTeleportPair(this.getId(), result -> {
                if (result == null) return;

                var first = result.getInt("teleport_one_id");
                var second = result.getInt("teleport_two_id");
                if (first != this.getId()) this.pairId = first;
                else if (second != this.getId()) this.pairId = second;
                else this.pairId = PairNotFound;
            });
        }

        return this.pairId > InvalidPair;
    }

    @Override
    public void onInteract(final IRoomEntity entity) {
        if (!this.isValidTeleport()) {
        }
    }
}
