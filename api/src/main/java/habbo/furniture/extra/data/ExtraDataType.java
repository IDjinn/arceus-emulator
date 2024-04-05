package habbo.furniture.extra.data;

import java.util.HashSet;

public enum ExtraDataType {
    Legacy(0),
    Map(1),
    String(2),
    Vote(3),
    Empty(4),
    Number(5),
    HighScore(6),
    Crackable(7),

    ListList(100),
    Int(101);

    static {
        final var types = new HashSet<ExtraDataType>();
        for (ExtraDataType type : values()) {
            if (types.contains(type))
                throw new IllegalArgumentException("Duplicate type: " + type);

            types.add(type);
        }
    }

    private int type;

    ExtraDataType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
