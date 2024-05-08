package habbo.habbos.data.messenger;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.messenger.IFriend;
import habbo.messenger.IMessengerManager;

import java.util.ArrayList;
import java.util.List;

public class HabboMessenger implements IHabboMessenger {
    private final IHabbo habbo;
    private final List<IFriend> friends;
    private final List<Integer> pendingRequests;

    @Inject
    private IMessengerManager messengerManager;

    public HabboMessenger(final IHabbo habbo) {
        this.habbo = habbo;
        this.friends = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();
    }

    @Override
    public void init() {
        this.friends.addAll(this.messengerManager.getAllFriendsOf(this.getHabbo()));
    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public void requestFriendship(final IHabbo habbo) {
        this.messengerManager.requestFriendship(this.getHabbo(), habbo);
    }

    @Override
    public void removeFriendship(final IHabbo habbo) {

    }

    @Override
    public void acceptFriendship(final IHabbo habbo) {

    }

    @Override
    public boolean friendHasRequestedFriendship(final IHabbo habbo) {
        return false;
    }
}
