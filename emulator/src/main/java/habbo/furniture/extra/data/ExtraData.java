package habbo.furniture.extra.data;

import habbo.rooms.components.objects.items.ILimitedData;
import habbo.rooms.components.objects.items.LimitedData;
import networking.packets.OutgoingPacket;

public abstract class ExtraData implements IExtraData {
    protected transient final int LTD_FLAG = 0xFF00;
    protected transient final int DATA_MASK = 0xFF;

    private final transient ExtraDataType dataType;
    private final int type;
    protected String data;
    private ILimitedData limitedData = LimitedData.NONE;

    public ExtraData(ExtraDataType type) {
        this.dataType = type;
        this.type = type.getType();
    }

    @Override
    public void serialize(OutgoingPacket packet) {
        packet.appendInt(this.getExtraDataType().getType() | (this.getLimitedData().isLimited() ? this.LTD_FLAG : 0));
        this.serializeState(packet);
        if (this.getLimitedData().isLimited()) {
            packet.appendInt(this.getLimitedData().getLimitedRare())
                    .appendInt(this.getLimitedData().getLimitedRareTotal());
        }
    }

    public abstract void serializeState(OutgoingPacket packet);
    
    @Override
    public ILimitedData getLimitedData() {
        return this.limitedData;
    }

    @Override
    public void setLimitedData(ILimitedData data) {
        this.limitedData = data;
    }

    @Override
    public ExtraDataType getExtraDataType() {
        return this.dataType;
    }

    public final class ExtraDataReader {
        public final int type;

        public ExtraDataReader(int type) {
            this.type = type;
        }
    }
}