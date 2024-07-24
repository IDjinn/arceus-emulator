package habbo.furniture.extra.data;

import networking.packets.OutgoingPacket;
import utils.gson.GsonHelper;

import java.util.ArrayList;
import java.util.List;


public class NumberExtraData extends ExtraData implements IExtraData {
    private static final int STATE_INDEX = 0;

    private final List<Integer> integers;

    public NumberExtraData() {
        super(ExtraDataType.Empty);
        this.integers = new ArrayList<>();
    }


    @Override
    public String toJson() {
        return GsonHelper.getGson().toJson(this);
    }

    @Override
    public void serializeValue(final OutgoingPacket<U> packet) {
        final int additionalKeys = 1;
        packet.appendInt(this.integers.size() + additionalKeys);
        packet.appendInt(this.getState());
        for (final var value : this.integers) {
            packet.appendInt(value);
        }
    }
}
