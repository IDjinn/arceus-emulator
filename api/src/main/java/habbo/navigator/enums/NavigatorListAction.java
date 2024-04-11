package habbo.navigator.enums;

public enum NavigatorListAction {
    NONE(0),
    MORE(1),
    BACK(2);

    private final int value;

    NavigatorListAction(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }
}
