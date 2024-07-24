package habbo.furniture;

public enum FurnitureType {
    FLOOR("S"),
    WALL("I"),
    EFFECT("E"),
    BADGE("B"),
    ROBOT("R"),
    HABBO_CLUB("H"),
    PET("P");

    public final String code;

    FurnitureType(String code) {
        this.code = code;
    }

    public static FurnitureType fromString(String code) {
        return switch (code.toUpperCase()) {
            case "I" -> WALL;
            case "E" -> EFFECT;
            case "B" -> BADGE;
            case "R" -> ROBOT;
            case "H" -> HABBO_CLUB;
            case "P" -> PET;
            default -> FLOOR;
        };
    }


    @Override
    public String toString() {
        return this.code;
    }
}