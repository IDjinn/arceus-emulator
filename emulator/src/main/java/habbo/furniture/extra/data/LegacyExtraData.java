package habbo.furniture.extra.data;

import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.gson.GsonHelper;


public class LegacyExtraData extends ExtraData implements IExtraData {

    public LegacyExtraData() {
        super(ExtraDataType.Legacy);
        this.data = "";
    }

    public LegacyExtraData(@NotNull String data) {
        super(ExtraDataType.Legacy);
        this.data = data;
    }

    public static LegacyExtraData fromLegacyString(String value) {
        return new LegacyExtraData().setData(value);
    }

    public LegacyExtraData setData(@Nullable String data) {
        this.data = data;
        return this;
    }

    @Override
    public void serializeState(OutgoingPacket packet) {
        packet.appendString(this.data);
    }

    @Override
    public String toJson() {
        return GsonHelper.getGson().toJson(this);
    }

    public int getStateValue() {
        try {
            return Integer.parseInt(this.data);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}