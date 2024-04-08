package habbo.navigator.enums;

public enum NavigatorLayoutDisplay {
    DEFAULT,
    COLLAPSED;


    public static NavigatorLayoutDisplay fromString(String type) {
        if(type.equalsIgnoreCase("collapsed")) {
            return COLLAPSED;
        }

        return DEFAULT;
    }
}
