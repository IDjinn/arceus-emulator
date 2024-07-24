package packets.dto.outgoing.purse;

import habbo.habbos.data.wallet.IWalletValue;
import networking.packets.IPacketDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record UserCurrencyComposerDTO(Collection<IWalletValue> currencies) implements IPacketDTO {
}
