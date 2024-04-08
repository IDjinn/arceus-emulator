package habbo.furniture.extra.data;

import networking.packets.OutgoingPacket;
import utils.gson.GsonHelper;


public class EmptyExtraData extends ExtraData implements IExtraData {
    public EmptyExtraData() {
        super(ExtraDataType.Empty);
    }


    @Override
    public String toJson() {
        return GsonHelper.getGson().toJson(this);
    }

    @Override
    public void serializeState(OutgoingPacket packet) {
        
    }
}
