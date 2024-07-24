package habbo.navigator.data;

import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.navigator.enums.NavigatorDisplayOrder;
import habbo.navigator.enums.NavigatorLayoutDisplay;
import habbo.navigator.enums.NavigatorListAction;
import habbo.rooms.IRoom;
import habbo.rooms.enums.RoomAccessState;
import networking.packets.IOutgoingPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record NavigatorResultCategory(
        int order,
        String category,
        String search,
        NavigatorListAction action,
        NavigatorDisplayMode mode,
        NavigatorLayoutDisplay hidden,
        List<IRoom> rooms,
        boolean filter,
        boolean showInvisible,
        NavigatorDisplayOrder displayOrder,
        int categoryOrder
) implements INavigatorResultCategory, Comparable<NavigatorResultCategory> {
    public void write(IOutgoingPacket<U> packet) {
        packet.appendString(this.category);
        packet.appendString(this.search);
        packet.appendInt(this.action.get());
        packet.appendBoolean(this.hidden.equals(NavigatorLayoutDisplay.COLLAPSED));
        packet.appendInt(this.mode.get());

        synchronized (this.rooms) {
            if (!this.showInvisible) {
                this.rooms.removeIf(room -> room.getData().getAccessState().equals(RoomAccessState.INVISIBLE));
            }

            packet.appendInt(this.rooms.size());
//                Collections.sort(this.rooms);
            for (final IRoom room : this.rooms) {
                room.write(packet);
            }
        }
    }

    public boolean filterRooms(INavigatorFilterType type, String search) {
        if(search.isEmpty()) return true;

        this.rooms.removeIf(room -> switch (type.getKey()) {
            case "anything" -> false;
            case "roomname" -> !room.getData().getName().toLowerCase().contains(search.toLowerCase());
            case "tag" -> !room.getData().getTags().contains(search);
            case "owner" -> !room.getData().getOwnerName().equalsIgnoreCase(search);
            case "desc" -> !room.getData().getDescription().toLowerCase().contains(search.toLowerCase());
            // TODO: "promo" and "group" cases
            default -> false;
        });

        return !this.rooms.isEmpty();
    }

    @Override
    public int compareTo(@NotNull NavigatorResultCategory o) {
        if (this.displayOrder != NavigatorDisplayOrder.ACTIVITY) {
            return this.categoryOrder - o.categoryOrder;
        }

        if (this.category.equalsIgnoreCase("popular")) {
            return -1;
        }

        return this.rooms.size() - o.rooms.size();
    }
}