package habbo.habbos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.IHotel;
import habbo.habbos.data.IHabboData;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Singleton
public class HabboManager implements IHabboManager {
    private final IHotel hotel;

    @Inject
    public HabboManager(IHotel hotel) {
        this.hotel = hotel;

        this.hotel.getProcessHandler().registerProcess(HabboManager.class.getSimpleName(), this::updateHabbos, 5, TimeUnit.MINUTES);
    }

    private void updateHabbos() {

    }

    @Override
    public Optional<IHabboData> getHabboData(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<IHabboData> getHabboData(int id) {
        return Optional.empty();
    }
}
