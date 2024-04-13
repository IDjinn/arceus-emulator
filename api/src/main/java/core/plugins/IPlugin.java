package core.plugins;

import com.google.inject.Injector;
import org.apache.maven.artifact.versioning.ComparableVersion;

public interface IPlugin {
    String getName();

    String getDescription();

    ComparableVersion getVersion();

    String getAuthor();

    void init(Injector injector);

    void destroy();
}
