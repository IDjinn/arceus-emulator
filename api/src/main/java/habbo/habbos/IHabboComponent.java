package habbo.habbos;

public interface IHabboComponent {

    void init();

    default void update() {

    }

    default void destory() {
    }

    IHabbo getHabbo();
}
