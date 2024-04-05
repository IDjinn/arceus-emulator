package habbo.furniture.extra.data;

import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.Nullable;
import utils.gson.GsonHelper;


public class LegacyExtraData extends ExtraData implements IExtraData {
    private @Nullable String data;

    public LegacyExtraData() {
        super(ExtraDataType.Legacy);
        this.data = "";
    }

    public static LegacyExtraData fromLegacyString(String value) {
        return new LegacyExtraData().setData(value);
    }

    public LegacyExtraData setData(@Nullable String data) {
        this.data = data;
        return this;
    }

    @Override
    public OutgoingPacket serialize(OutgoingPacket packet) {
        return packet
                .appendInt(this.getExtraDataType().getType())
                .appendString(data);
    }

    @Override
    public String toJson() {
        return GsonHelper.getGson().toJson(this);
    }
}