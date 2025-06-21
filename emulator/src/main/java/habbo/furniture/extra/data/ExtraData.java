package habbo.furniture.extra.data;

import habbo.rooms.components.objects.items.ILimitedData;
import habbo.rooms.components.objects.items.LimitedData;
import lombok.Getter;
import lombok.Setter;
import networking.packets.OutgoingPacket;

@SuppressWarnings("UnnecessaryModifier")
public abstract class ExtraData implements IExtraData {
    public static transient final int LTD_FLAG = 0xFF00;
    public static transient final int DATA_MASK = 0xFF;

    @Getter
    @Setter
    private int state = 0;

    private LimitedData limitedData = LimitedData.NONE;
    private final transient ExtraDataType dataType;
    private final int type;

    public ExtraData(ExtraDataType type) {
        this.dataType = type;
        this.type = type.getType();
    }

    @Override
    public void serialize(OutgoingPacket packet) {
        packet.appendInt(this.getExtraDataType().getType() | (this.getLimitedData().isLimited() ? LTD_FLAG : 0));
        this.serializeValue(packet);
        if (this.getLimitedData().isLimited()) {
            packet.appendInt(this.getLimitedData().limitedRare())
                    .appendInt(this.getLimitedData().limitedRareTotal());
        }
    }

    @Override
    public void setLimitedData(ILimitedData data) {
        this.limitedData = (LimitedData) data;
    }

    @Override
    public void serializeState(final OutgoingPacket packet) {
        packet.appendString(String.valueOf(this.state));
    }

    @Override
    public ILimitedData getLimitedData() {
        return this.limitedData;
    }

    @Override
    public ExtraDataType getExtraDataType() {
        return this.dataType;
    }

    public abstract void serializeValue(final OutgoingPacket packet);

    public final class ExtraDataReader {
        public final int type;

        public ExtraDataReader(int type) {
            this.type = type;
        }
    }
}