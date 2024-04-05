package habbo.furniture.extra.data;

public abstract class ExtraData implements IExtraData {
    protected transient final int LTD_FLAG = 0xFF00;
    protected transient final int DATA_MASK = 0xFF;
    
    private final transient ExtraDataType dataType;
    private final int type;
    public ExtraData(ExtraDataType type) {
        this.dataType = type;
        this.type = type.getType();
    }

    @Override
    public ExtraDataType getExtraDataType() {
        return dataType;
    }

    public class ExtraDataReader {
        public final int type;

        public ExtraDataReader(int type) {
            this.type = type;
        }
    }
}
