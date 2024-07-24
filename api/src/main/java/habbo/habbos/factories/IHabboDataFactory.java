package habbo.habbos.factories;

import habbo.habbos.data.IHabboData;
import storage.results.IConnectionResult;

public interface IHabboDataFactory {
    IHabboData create(IConnectionResult data);
}
