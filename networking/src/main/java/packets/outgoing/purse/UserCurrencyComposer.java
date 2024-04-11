package packets.outgoing.purse;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserCurrencyComposer extends OutgoingPacket {
    public UserCurrencyComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserCurrencyComposer);

        appendInt(habbo.getWallet().getWalletCurrencies().size());
        for (var currency : habbo.getWallet().getWalletCurrencies().values()) {
            appendInt(currency.getCurrencyType());
            appendInt(currency.getAmount());
        }
    }
}
