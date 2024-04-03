package habbo.rooms.components.objects.items;

public class LimitedData implements ILimitedData {
    public static final LimitedData NONE = new LimitedData(0, 0, 0);

    private final long itemId;
    private final int limitedRare;
    private final int limitedRareTotal;

    public LimitedData(long itemId, int limitedRare, int limitedRareTotal) {
        this.itemId = itemId;
        this.limitedRare = limitedRare;
        this.limitedRareTotal = limitedRareTotal;
    }

    public long getItemId() {
        return itemId;
    }

    public int getLimitedRare() {
        return limitedRare;
    }

    public int getLimitedRareTotal() {
        return limitedRareTotal;
    }
}
