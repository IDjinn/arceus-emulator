package packets.outgoing.navigator;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class NewNavigatorCollapsedCategoriesComposer extends OutgoingPacket {
    public NewNavigatorCollapsedCategoriesComposer() {
        super(OutgoingHeaders.NewNavigatorCollapsedCategoriesComposer);

        this.appendInt(46);

        this.appendString("new_ads");
        this.appendString("friend_finding");
        this.appendString("staffpicks");
        this.appendString("with_friends");
        this.appendString("with_rights");
        this.appendString("query");
        this.appendString("recommended");
        this.appendString("my_groups");
        this.appendString("favorites");
        this.appendString("history");
        this.appendString("top_promotions");
        this.appendString("campaign_target");
        this.appendString("friends_rooms");
        this.appendString("groups");
        this.appendString("metadata");
        this.appendString("history_freq");
        this.appendString("highest_score");
        this.appendString("competition");
        this.appendString("category__Agencies");
        this.appendString("category__Role Playing");
        this.appendString("category__Global Chat & Discussi");
        this.appendString("category__GLOBAL BUILDING AND DE");
        this.appendString("category__global party");
        this.appendString("category__global games");
        this.appendString("category__global fansite");
        this.appendString("category__global help");
        this.appendString("category__Trading");
        this.appendString("category__global personal space");
        this.appendString("category__Habbo Life");
        this.appendString("category__TRADING");
        this.appendString("category__global official");
        this.appendString("category__global trade");
        this.appendString("category__global reviews");
        this.appendString("category__global bc");
        this.appendString("category__global personal space");
        this.appendString("eventcategory__Hottest Events");
        this.appendString("eventcategory__Parties & Music");
        this.appendString("eventcategory__Role Play");
        this.appendString("eventcategory__Help Desk");
        this.appendString("eventcategory__Trading");
        this.appendString("eventcategory__Games");
        this.appendString("eventcategory__Debates & Discuss");
        this.appendString("eventcategory__Grand Openings");
        this.appendString("eventcategory__Friending");
        this.appendString("eventcategory__Jobs");
        this.appendString("eventcategory__Group Events");
    }
}
