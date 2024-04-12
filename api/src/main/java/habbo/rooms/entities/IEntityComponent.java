package habbo.rooms.entities;

public interface IEntityComponent {
    void init();

    default void onLoaded() {

    }

    void destroy();
}
