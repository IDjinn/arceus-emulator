package repositories.habbo;

import com.google.inject.Singleton;
import habbo.habbos.data.IHabboData;
import habbo.habbos.data.IHabboSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queries.habbo.HabboQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboRepository;
import storage.results.IConnectionBooleanResultConsumer;
import storage.results.IConnectionResultConsumer;

@Singleton
public class HabboRepository extends ConnectionRepository implements IHabboRepository {
    protected Logger logger = LogManager.getLogger();

    public void getHabboIdByAuthTicket(IConnectionResultConsumer consumer, String authTicket) {
        this.select(HabboQuery.GET_ID_BY_AUTH_TICKET.get(), consumer, authTicket);
    }

    public void getHabboDataByAuthTicket(IConnectionResultConsumer consumer, String authTicket) {
        this.select(HabboQuery.GET_ALL_DATA_BY_AUTH_TICKET.get(), consumer, authTicket);
    }

    @Override
    public void saveHabboData(final IConnectionBooleanResultConsumer consumer, final IHabboData habboData) {
        this.update(HabboQuery.UPDATE_HABBO_DATA.get(), consumer,
                habboData.getUsername(),
                habboData.getEmail(),
                habboData.getAccountCreated(),
                habboData.getLastLogin(),
                habboData.getLastOnline(),
                habboData.getMotto(),
                habboData.getLook(),
                habboData.getGender(),
                habboData.getRank(),
                habboData.getCredits(),
                habboData.isOnline() ? "1" : "0",
                habboData.getAuthTicket(),
                habboData.getRegisterIp(),
                habboData.getCurrentIp(),
                habboData.getMachineId(),
                habboData.getHomeRoom(),
                habboData.getId()
        );
    }

    @Override
    public void saveHabboSettings(final IConnectionBooleanResultConsumer consumer, final IHabboSettings habboSettings) {

    }
}
