package habbo.rooms.components.objects.items;

public class LimitedData implements ILimitedData {
    public static final LimitedData NONE = new LimitedData(0, 0);

    private final int limitedRare;
    private final int limitedRareTotal;

    public LimitedData(int limitedRare, int limitedRareTotal) {
        this.limitedRare = limitedRare;
        this.limitedRareTotal = limitedRareTotal;
    }

    public static LimitedData fromString(String limitedData) {
        try {
            String[] split = limitedData.split(":");
            return new LimitedData(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        } catch (Exception e) {
            return NONE;
        }
    }

    public int getLimitedRare() {
        return this.limitedRare;
    }

    public int getLimitedRareTotal() {
        return this.limitedRareTotal;
    }

    @Override
    public boolean isLimited() {
        return this.limitedRare > 0 && this.limitedRareTotal > 0;
    }
}
