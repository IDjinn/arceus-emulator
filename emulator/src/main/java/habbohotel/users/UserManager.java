package habbohotel.users;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class UserManager implements IUserManager {
    @Override
    public boolean tryLoginWithSSO(@NotNull String sso) {
        return true;
    }
}
