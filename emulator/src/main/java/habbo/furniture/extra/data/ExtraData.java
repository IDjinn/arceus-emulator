package habbo.furniture.extra.data;

import habbo.rooms.components.objects.items.ILimitedData;
import habbo.rooms.components.objects.items.LimitedData;
import networking.packets.IPacketWriter;

@SuppressWarnings("UnnecessaryModifier")
public abstract class ExtraData implements IExtraData {
    public static transient final int LTD_FLAG = 0xFF00;
    public static transient final int DATA_MASK = 0xFF;

    private final transient ExtraDataType dataType;
    private final int type;
    private int state = 0;
    private LimitedData limitedData = LimitedData.NONE;

    public ExtraData(ExtraDataType type) {
        this.dataType = type;
        this.type = type.getType();
    }

    @Override
    public void serialize(IPacketWriter writer) {
        writer.appendInt(this.getExtraDataType().getType() | (this.getLimitedData().isLimited() ? LTD_FLAG : 0));
        this.serializeValue(writer);
        if (this.getLimitedData().isLimited()) {
            writer.appendInt(this.getLimitedData().limitedRare())
                    .appendInt(this.getLimitedData().limitedRareTotal());
        }
    }

    @Override
    public void setLimitedData(ILimitedData data) {
        this.limitedData = (LimitedData) data;
    }

    @Override
    public void serializeState(IPacketWriter writer) {
        writer.appendString(String.valueOf(this.state));
    }

    @Override
    public ILimitedData getLimitedData() {
        return this.limitedData;
    }

    public int getState() {
        return this.state;
    }

    @Override
    public ExtraDataType getExtraDataType() {
        return this.dataType;
    }

    public void setState(final int state) {
        this.state = state;
    }

    public abstract void serializeValue(final IPacketWriter packet);

    public final class ExtraDataReader {
        public final int type;

        public ExtraDataReader(int type) {
            this.type = type;
        }
    }
}