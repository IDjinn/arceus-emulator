package habbo.furniture.extra.data;

import networking.packets.OutgoingPacket;
import utils.gson.GsonHelper;

import java.util.HashMap;
import java.util.Map;


public class MapExtraData extends ExtraData implements IExtraData {
    private static final String STATE = "state";
    private static final String RARITY = "rarity";

    private final Map<String, String> values;

    public MapExtraData() {
        super(ExtraDataType.Map);
        this.values = new HashMap<>();
    }

    public MapExtraData(int state) {
        this();
        this.setState(state);
    }

    public static MapExtraData fromExtraData(final IExtraData extraData) {
        switch (extraData.getExtraDataType()) {
            case Legacy -> {
                return new MapExtraData(extraData.getState());
            }
            case Map -> {
                return (MapExtraData) extraData;
            }
            default -> {
                return new MapExtraData();
            }
        }
    }


    @Override
    public void serializeValue(OutgoingPacket<U> packet) {
        final int additionalKeys = 2;
        packet.appendInt(this.values.size() + additionalKeys);
        for (Map.Entry<String, String> entry : this.values.entrySet()) {
            packet.appendString(entry.getKey());
            packet.appendString(entry.getValue());
        }

        {
            packet.appendString(STATE);
            packet.appendString(String.valueOf(this.getState()));
        }
        {
            packet.appendString(RARITY);
            packet.appendString(String.valueOf(this.getLimitedData().limitedRare()));
        }
    }

    @Override
    public String toJson() {
        return GsonHelper.getGson().toJson(this);
    }

    public Map<String, String> getValues() {
        return this.values;
    }
}
