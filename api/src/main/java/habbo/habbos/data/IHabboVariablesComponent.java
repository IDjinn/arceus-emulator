package habbo.habbos.data;

import habbo.habbos.IHabbo;
import habbo.habbos.IHabboComponent;
import habbo.variables.IVariableManager;

public interface IHabboVariablesComponent extends IVariableManager, IHabboComponent {
    IHabbo getHabbo();
}
