package habbo.habbos;

import habbo.habbos.data.IHabboData;

import java.util.Optional;

public interface IHabboManager {
    public Optional<IHabboData> getHabboData(String name);

    public Optional<IHabboData> getHabboData(int id);
    
}
