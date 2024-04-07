package habbo.navigator.data;

import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.navigator.enums.NavigatorDisplayOrder;
import habbo.navigator.enums.NavigatorLayoutDisplay;
import habbo.navigator.enums.NavigatorListAction;
import habbo.rooms.IRoom;
import habbo.rooms.enums.RoomAccessState;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    public void write(OutgoingPacket packet) {
        packet.appendString(this.category);
        packet.appendString(this.search);
        packet.appendInt(this.action.get());
        packet.appendBoolean(this.hidden.equals(NavigatorLayoutDisplay.COLLAPSED));
        packet.appendInt(this.mode.get());

        synchronized (this.rooms) {
            if (!this.showInvisible) {
                var toRemove = new ArrayList<IRoom>();
                for (var room : this.rooms) {
                    if (room.getData().getAccessState().equals(RoomAccessState.INVISIBLE))
                        toRemove.add(room);
                }

                this.rooms.removeAll(toRemove);
            }

            packet.appendInt(this.rooms.size());
//                Collections.sort(this.rooms);
            for (var room : this.rooms)
                room.serialize(packet);
        }
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