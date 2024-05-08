package habbo.messenger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.habbos.IHabbo;
import habbo.habbos.IHabboManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.repositories.messenger.IMessengerRepository;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class MessengerManager implements IMessengerManager {
    private static final Logger LOGGER = LogManager.getLogger();
    @Inject
    private IMessengerRepository messengerRepository;
    @Inject
    private IHabboManager habboManager;

    @Override
    public int getMaximumFriendCount() {
        return 300;
    }

    @Override
    public List<IFriend> searchForFriend(final String query) {
        return List.of();
    }

    @Override
    public List<IFriend> getAllFriendsOf(final IHabbo habbo) {
        final var friends = new ArrayList<IFriend>();
        this.messengerRepository.getAllFriendsOfHabbo(habbo.getData().getId(), result -> {
            if (result == null) return;

            final int otherUserId = result.getInt("user_two_id");
            final int type = result.getInt("relation");
            final var friendData = habboManager.getHabboData(otherUserId);
            if (friendData.isEmpty()) {
                LOGGER.error(STR."friend \{otherUserId} of habbo \{habbo.getData().getId()} not found");
                return;
            }

            friends.add(new Friend(friendData.get(), RelationshipType.fromType(type)));
        });

        LOGGER.trace("Habbo {} loaded total of {} friends", habbo.getData().getId(), friends.size());
        return friends;
    }
}
