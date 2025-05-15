package habbo.rooms.components.objects.items;

public record LimitedData(int limitedRare, int limitedRareTotal) implements ILimitedData {
    public static final LimitedData NONE = new LimitedData(0, 0);

    public static LimitedData fromString(String limitedData) {
        try {
            String[] split = limitedData.split(":");
            return new LimitedData(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        } catch (Exception e) {
            return NONE;
        }
    }

    @Override
    public String toString() {
        return limitedRare + ":" + limitedRareTotal;
    }

    @Override
    public boolean isLimited() {
        return this.limitedRare > 0 && this.limitedRareTotal > 0;
    }
}