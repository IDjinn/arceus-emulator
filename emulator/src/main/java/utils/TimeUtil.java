package utils;

public abstract class TimeUtil {
    /**
     * Get the current time in Unix Timestamp
     */
    public static long unixNow() {
        return (int) (System.currentTimeMillis() / 1000L);
    }
}
