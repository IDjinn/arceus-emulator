package habbo.permissions;

import com.google.inject.AbstractModule;

public class PermissionModule extends AbstractModule {
    @Override
    public void configure() {
        this.bind(IPermissionManager.class).to(PermissionManager.class);
    }
}
