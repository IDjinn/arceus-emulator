package habbo.messenger;

import habbo.habbos.data.IHabboData;
import networking.util.ISerializable;

public interface IFriend extends ISerializable {
    IHabboData getHabboData();

    boolean isInRoom();

    boolean isOnline();

    RelationshipType getRelationship();
}
