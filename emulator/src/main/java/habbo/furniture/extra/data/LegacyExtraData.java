package habbo.furniture.extra.data;

import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.Nullable;
import utils.gson.GsonHelper;


public class LegacyExtraData extends ExtraData implements IExtraData {

    public LegacyExtraData() {
        super(ExtraDataType.Legacy);
    }

    public static LegacyExtraData fromLegacyString(String value) {
        return new LegacyExtraData().setData(value);
    }

    public LegacyExtraData setData(@Nullable String data) {
        this.data = data;
        return this;
    }

    @Override
    public void serializeData(OutgoingPacket packet) {
        packet.appendString(data);
    }

    @Override
    public String toJson() {
        return GsonHelper.getGson().toJson(this);
    }
}