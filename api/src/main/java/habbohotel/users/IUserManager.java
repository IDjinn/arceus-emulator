package habbohotel.users;

import org.jetbrains.annotations.NotNull;

public interface IUserManager {
    public boolean tryLoginWithSSO(@NotNull String sso);
}
