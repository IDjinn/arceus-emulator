package habbo.messenger;

import habbo.habbos.IHabbo;

import java.util.List;

public interface IMessengerManager {
    int getMaximumFriendCount();


    List<IFriend> searchForFriend(String query);

    List<IFriend> getAllFriendsOf(IHabbo habbo);
}
