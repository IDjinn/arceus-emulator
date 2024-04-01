package repositories.users;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.ConnectionRepository;
import storage.repositories.users.IUserRepository;

@Singleton
public class UserRepository extends ConnectionRepository implements IUserRepository {
    protected Logger logger = LogManager.getLogger();

    public void getUserByAuthTicket(String authTicket) {
        this.select("SELECT * FROM users WHERE auth_ticket = ?", connectionResult -> {
            int id = connectionResult.getInt("id");

            String username = connectionResult.getString("username");

            logger.info(STR."User with id \{id} and username \{username} found by auth ticket \{authTicket}");
        }, authTicket);
    }
}
