package habbo.habbos.data.wallet;

public interface IWalletValue {
    int getCurrencyType();

    int getAmount();

    void setAmount(int amount);
}
