package networking.util;

public record ReleaseVersion(
        String production,
        String type,
        int platform,
        int category
) {
}