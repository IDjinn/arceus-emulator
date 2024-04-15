package core.plugins;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.apache.maven.artifact.versioning.ComparableVersion;

public interface IPlugin extends Module {
    String getName();

    String getDescription();

    ComparableVersion getVersion();

    String getAuthor();

    void init();

    void destroy();

    Injector getInjector();
}
