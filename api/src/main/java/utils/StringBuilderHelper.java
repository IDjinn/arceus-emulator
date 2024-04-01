package utils;

public class StringBuilderHelper {
    private static final ThreadLocal<StringBuilder> stringBuilderLocal = ThreadLocal.withInitial(() -> new StringBuilder(1024));

    public static StringBuilder getBuilder() {
        var sb = stringBuilderLocal.get();
        sb.setLength(0);
        return sb;
    }
}
