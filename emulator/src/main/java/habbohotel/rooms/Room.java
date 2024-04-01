package habbohotel.rooms;

import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;

public class Room implements IRoom {
    private int id;
    private String name;
    private String password;
    private int maxUsers;

    public Room(int roomId, String roomName) {
        this.id = roomId;
        this.name = roomName;
        this.maxUsers = 0;
        this.password = "";
    }


    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public int getMaxUsers() {
        return 0;
    }

    @Override
    public void setMaxUsers(int maxUsers) {

    }

    @Override
    public int getMinUsers() {
        return 0;
    }

    @Override
    public void setMinUsers(int minUsers) {

    }

    @Override
    public boolean isPublic() {
        return false;
    }

    @Override
    public void setPublic(boolean isPublic) {

    }

    @Override
    public RoomAccess getRoomAccess() {
        return RoomAccess.Open;
    }

    @Override
    public void setRoomAccess(RoomAccess roomAccess) {

    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public int compareTo(@NotNull IRoom o) {
        return o.getId() - id;
    }


    @Override
    public void serialize(OutgoingPacket packet) {
        packet.appendInt(this.id);
        packet.appendString(this.name);
//        if (this.isPublic()) { TODO
        packet.appendInt(0);
        packet.appendString("");
//        } else {
//            packet.appendInt(this.ownerId);
//            packet.appendString(this.ownerName);
//        }
        packet.appendInt(this.getRoomAccess().getState());
        packet.appendInt(0);
        packet.appendInt(this.getMaxUsers());
        packet.appendString("this.description");
        packet.appendInt(0);
        packet.appendInt(0);//this.score
        packet.appendInt(0);
        packet.appendInt(0); // this.category

//        String[] tags = Arrays.stream(this.tags.split(";")).filter(t -> !t.isEmpty()).toArray(String[]::new);
//        packet.appendInt(tags.length);
//        for (String s : tags) {
//            packet.appendString(s);
//        }
        packet.appendInt(0);

        int base = 0;

//        if (this.getGuildId() > 0) {
//            base = base | 2;
//        }
//
//        if (this.isPromoted()) {
//            base = base | 4;
//        }
//
//        if (!this.isPublicRoom()) {
//            base = base | 8;
//        }


        packet.appendInt(base);


//        if (this.getGuildId() > 0) {
//            Guild g = Emulator.getGameEnvironment().getGuildManager().getGuild(this.getGuildId());
//            if (g != null) {
//                packet.appendInt(g.getId());
//                packet.appendString(g.getName());
//                packet.appendString(g.getBadge());
//            } else {
//                packet.appendInt(0);
//                packet.appendString("");
//                packet.appendString("");
//            }
//        }
//
//        if (this.promoted) {
//            packet.appendString(this.promotion.getTitle());
//            packet.appendString(this.promotion.getDescription());
//            packet.appendInt((this.promotion.getEndTimestamp() - Emulator.getIntUnixTimestamp()) / 60);
//        }

    }
}
