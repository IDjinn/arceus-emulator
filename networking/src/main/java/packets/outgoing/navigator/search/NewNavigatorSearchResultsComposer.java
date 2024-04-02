package packets.outgoing.navigator.search;

import habbo.rooms.IRoom;
import habbo.rooms.RoomAccess;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;
import packets.outgoing.OutgoingHeaders;

import java.util.ArrayList;
import java.util.List;

public class NewNavigatorSearchResultsComposer extends OutgoingPacket {

    public NewNavigatorSearchResultsComposer(String code, String query, List<IRoom> rooms) {
        super(OutgoingHeaders.NewNavigatorSearchResultsComposer);

        appendString(code);
        appendString(query);

        appendInt(rooms.size());
        var searchResult = new SearchResultList(0,
                code,
                query,
                SearchAction.NONE,
                ListMode.FORCED_THUNBNAILS,
                DisplayMode.VISIBLE,
                rooms,
                true,
                true,
                DisplayOrder.ACTIVITY,
                -1
        );
        searchResult.serialize(this);
    }

    public enum DisplayOrder {
        ORDER_NUM,
        ACTIVITY
    }

    public enum DisplayMode {
        VISIBLE,
        COLLAPSED
    }

    public enum SearchAction {
        NONE(0),
        MORE(1),
        BACK(2);

        public final int type;

        SearchAction(int type) {
            this.type = type;
        }
    }

    public enum ListMode {
        LIST(0),
        THUMBNAILS(1),
        FORCED_THUNBNAILS(2);

        public final int type;

        ListMode(int type) {
            this.type = type;
        }

        public static ListMode fromType(int type) {
            for (ListMode m : ListMode.values()) {
                if (m.type == type) {
                    return m;
                }
            }

            return LIST;
        }
    }

    public class SearchResultList implements ISerializable, Comparable<SearchResultList> {
        public final int order;
        public final String code;
        public final String query;
        public final SearchAction action;
        public final ListMode mode;
        public final DisplayMode hidden;
        public final List<IRoom> rooms;
        public final boolean filter;
        public final boolean showInvisible;
        public final DisplayOrder displayOrder;
        public final int categoryOrder;

        public SearchResultList(int order, String code, String query, SearchAction action, ListMode mode, DisplayMode hidden, List<IRoom> rooms, boolean filter, boolean showInvisible, DisplayOrder displayOrder, int categoryOrder) {
            this.order = order;
            this.code = code;
            this.query = query;
            this.action = action;
            this.mode = mode;
            this.rooms = rooms;
            this.hidden = hidden;
            this.filter = filter;
            this.showInvisible = showInvisible;
            this.displayOrder = displayOrder;
            this.categoryOrder = categoryOrder;
        }

        @Override
        public void serialize(OutgoingPacket packet) {
            packet.appendString(this.code); //Search Code
            packet.appendString(this.query); //Text
            packet.appendInt(this.action.type); //Action Allowed (0 (Nothing), 1 (More Results), 2 (Go Back))
            packet.appendBoolean(this.hidden.equals(DisplayMode.COLLAPSED)); //Closed
            packet.appendInt(this.mode.type); //Display Mode (0 (List), 1 (Thumbnails), 2 (Thumbnail no choice))

            synchronized (this.rooms) {
                if (!this.showInvisible) {
                    var toRemove = new ArrayList<IRoom>();
                    for (var room : this.rooms) {
                        if (room.getRoomAccess().equals(RoomAccess.Invisible))
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
        public int compareTo(SearchResultList o) {
            if (this.displayOrder == DisplayOrder.ACTIVITY) {
                if (this.code.equalsIgnoreCase("popular")) {
                    return -1;
                }

                return this.rooms.size() - o.rooms.size();
            }
            return this.categoryOrder - o.categoryOrder;
        }
    }
}
