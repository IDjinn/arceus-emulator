package habbo.habbos.data.wallet;

import habbo.habbos.IHabboComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IHabboWallet extends IHabboComponent {
    public Map<Integer, IWalletValue> getWalletCurrencies();

    @Nullable
    public IWalletValue getWalletCurrency(int currencyType);
}
