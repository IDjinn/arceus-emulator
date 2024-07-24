package habbo.habbos.data.wallet;

import habbo.habbos.IHabboComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IHabboWallet extends IHabboComponent {
    Map<Integer, IWalletValue> getWalletCurrencies();

    @Nullable
    IWalletValue getWalletCurrency(int currencyType);
}
