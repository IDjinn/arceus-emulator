package habbo.habbos.data.wallet;

public class WalletValue implements IWalletValue {
    private final int currencyType;
    private int amount;

    public WalletValue(int currencyType, int amount) {
        this.currencyType = currencyType;
        this.amount = amount;
    }

    @Override
    public int getCurrencyType() {
        return this.currencyType;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(final int amount) {
        this.amount = amount;
    }
}
