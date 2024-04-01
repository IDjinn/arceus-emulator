package habbohotel.users.providers;

import com.google.inject.Singleton;

@Singleton
public class UserLoginProvider implements ILoginProvider {
    public boolean handle(String authTicket) {
        return true;
    }
}
