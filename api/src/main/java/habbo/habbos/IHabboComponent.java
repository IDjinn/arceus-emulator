package habbo.habbos;

public interface IHabboComponent {

    void init();

    default void update() {

    }

    default void destory() {
    }

    IHabbo getHabbo();

    void requestFriendship(IHabbo habbo);
    
    void removeFriendship(IHabbo habbo);

    void acceptFriendship(IHabbo habbo);

    boolean friendHasRequestedFriendship(IHabbo habbo);
}
