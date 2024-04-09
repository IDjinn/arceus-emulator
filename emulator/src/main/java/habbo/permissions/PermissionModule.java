package habbo.permissions;

import com.google.inject.AbstractModule;

public class PermissionModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IPermissionManager.class).to(PermissionManager.class);
    }
}
