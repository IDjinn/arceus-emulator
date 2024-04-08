package habbo.navigator.enums;

public enum NavigatorDisplayMode {
    LIST(0),
    THUMBNAILS(1),
    FORCED_THUMBNAILS(2);

    private final int value;

    NavigatorDisplayMode(int value) {
        this.value = value;
    }

    public static NavigatorDisplayMode fromType(int type) {
        return switch (type) {
            case 1 -> THUMBNAILS;
            case 2 -> FORCED_THUMBNAILS;
            default -> LIST;
        };
    }

    public static NavigatorDisplayMode fromString(String type) {
        return switch (type.toUpperCase()) {
            case "THUMBNAILS" -> THUMBNAILS;
            case "FORCED_THUMBNAILS" -> FORCED_THUMBNAILS;
            default -> LIST;
        };
    }

    public int get() {
        return this.value;
    }
}
