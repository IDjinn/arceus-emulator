import com.google.inject.Injector;
import core.plugins.IPlugin;
import org.apache.maven.artifact.versioning.ComparableVersion;

public class WiredPlugin implements IPlugin {
    private static final String author = "IDjinn";
    private static final ComparableVersion version = new ComparableVersion("1.0.0");
    private static final String description = "All habbo original wireds and some customs";
    private static final String name = "wireds-plugin";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ComparableVersion getVersion() {
        return version;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void init(Injector injector) {

    }

    @Override
    public void destroy() {

    }
}
