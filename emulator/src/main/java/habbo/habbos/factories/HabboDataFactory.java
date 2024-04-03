package habbo.habbos.factories;

import com.google.inject.Singleton;
import habbo.habbos.data.HabboData;
import habbo.habbos.data.IHabboData;
import storage.results.IConnectionResult;

@Singleton
public class HabboDataFactory implements IHabboDataFactory {
    @Override
    public IHabboData create(IConnectionResult data) {
        return new HabboData(data);
    }
}
