package habbo.habbos;

import habbo.habbos.data.IHabboData;

import java.util.Optional;

public interface IHabboManager {
    Optional<IHabboData> getHabboData(String name);

    Optional<IHabboData> getHabboData(int id);
    
}
