package repositories.habbo;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queries.habbo.HabboQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class HabboRepository extends ConnectionRepository implements IHabboRepository {
    protected Logger logger = LogManager.getLogger();

    public void getHabboIdByAuthTicket(String authTicket, IConnectionResultConsumer consumer) {
        this.select(HabboQuery.GET_ID_BY_AUTH_TICKET.get(), consumer, authTicket);
    }

    public void getHabboDataByAuthTicket(String authTicket, IConnectionResultConsumer consumer) {
        this.select(HabboQuery.GET_ALL_DATA_BY_AUTH_TICKET.get(), consumer, authTicket);
    }
}
