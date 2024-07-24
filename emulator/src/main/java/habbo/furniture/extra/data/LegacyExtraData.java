package habbo.furniture.extra.data;

import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.gson.GsonHelper;


public class LegacyExtraData extends ExtraData implements IExtraData {
    private String legacyData = "";
    public LegacyExtraData() {
        super(ExtraDataType.Legacy);
    }

    public LegacyExtraData(@NotNull String data) {
        super(ExtraDataType.Legacy);
        this.legacyData = data;
    }

    public static LegacyExtraData fromLegacyString(String value) {
        return new LegacyExtraData().setLegacyData(value);
    }

    @Override
    public void serializeValue(final OutgoingPacket<U> packet) {
        packet.appendString(this.legacyData);
    }

    public LegacyExtraData setLegacyData(@Nullable String data) {
        this.legacyData = data;
        return this;
    }

    @Override
    public String toJson() {
        return GsonHelper.getGson().toJson(this);
    }
}