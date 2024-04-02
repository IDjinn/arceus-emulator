package repositories.habbo;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queries.habbo.HabboQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboRepository;
import storage.results.IConnectionResult;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class HabboRepository extends ConnectionRepository implements IHabboRepository {
    protected Logger logger = LogManager.getLogger();

    public int getHabboIdByAuthTicket(String authTicket) {
        final AtomicInteger result = new AtomicInteger(-1);

        this.select(HabboQuery.GET_ID_BY_AUTH_TICKET.get(), connectionResult -> {
            if(connectionResult == null) return;

            result.set(connectionResult.getInt("id"));
        }, authTicket);

        return result.get();
    }

    public IConnectionResult getHabboDataByAuthTicket(String authTicket) {
        final AtomicReference<IConnectionResult> result = new AtomicReference<>(null);

        this.select(HabboQuery.GET_ALL_DATA_BY_AUTH_TICKET.get(), connectionResult -> {
            if(connectionResult == null) return;

            result.set(connectionResult);
        }, authTicket);

        return result.get();
    }
}
