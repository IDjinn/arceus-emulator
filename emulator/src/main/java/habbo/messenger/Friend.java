package habbo.messenger;

import habbo.habbos.data.IHabboData;
import networking.packets.OutgoingPacket;

public class Friend implements IFriend {
    private final IHabboData data;
    private final RelationshipType type;

    public Friend(final IHabboData data, final RelationshipType type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public IHabboData getHabboData() {
        return this.data;
    }

    @Override
    public boolean isInRoom() {
        return false;
    }

    @Override
    public boolean isOnline() {
        return this.getHabboData().isOnline();
    }

    @Override
    public RelationshipType getRelationship() {
        return this.type;
    }

    @Override
    public void serialize(final OutgoingPacket packet) {

    }
}
