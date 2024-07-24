package utils.security;

import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;

public final class Sanitizer {
    public static final Cleaner PlainText = new Cleaner(Safelist.none());
    public static final Cleaner MarkText = new Cleaner(Safelist.basic().removeTags("a"));
    public static final Cleaner MarkWithImagesText = new Cleaner(Safelist.basicWithImages());
}
