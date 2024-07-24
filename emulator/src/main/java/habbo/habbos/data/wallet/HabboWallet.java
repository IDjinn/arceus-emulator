package habbo.habbos.data.wallet;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import org.jetbrains.annotations.Nullable;
import storage.repositories.habbo.IHabboWalletRepository;

import java.util.Map;

public class HabboWallet implements IHabboWallet {
    private final IHabbo habbo;
    private final Map<Integer, IWalletValue> walletCurrencies;

    @Inject
    private IHabboWalletRepository walletRepository;

    public HabboWallet(final IHabbo habbo) {
        this.habbo = habbo;

        this.walletCurrencies = new java.util.HashMap<>();
    }

    @Override
    public Map<Integer, IWalletValue> getWalletCurrencies() {
        return this.walletCurrencies;
    }

    @Nullable
    @Override
    public IWalletValue getWalletCurrency(final int currencyType) {
        return this.walletCurrencies.get(currencyType);
    }

    @Override
    public void init() {
        this.walletRepository.getHabboCurrencies(result -> {
            if (result == null) {
                this.createInitialData();
                return;
            }

            var currencyType = result.getInt("type");
            var amount = result.getInt("amount");
            this.walletCurrencies.put(currencyType, new WalletValue(currencyType, amount));
        }, this.getHabbo().getData().getId());
    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }

    private void createInitialData() { // TODO DIAMONDS
    }
}
