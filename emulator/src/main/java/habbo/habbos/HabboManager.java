package habbo.habbos;

import com.google.inject.Singleton;
import habbo.habbos.data.IHabboData;

import java.util.Optional;

@Singleton
public class HabboManager implements IHabboManager {
    @Override
    public Optional<IHabboData> getHabboData(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<IHabboData> getHabboData(int id) {
        return Optional.empty();
    }
}
