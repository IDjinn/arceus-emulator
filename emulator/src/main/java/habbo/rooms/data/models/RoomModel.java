package habbo.rooms.data.models;

import habbo.rooms.data.models.IRoomModel;
import storage.results.IConnectionResult;

public class RoomModel implements IRoomModel {
    private final IRoomModelData data;

    public RoomModel(IConnectionResult result) {
        this.data = new RoomModelData(result);

        try {
            this.calculateModel();
        } catch (Exception e) {
            this.getData().getLogger().error(STR."Error while calculating model: \{e.getMessage()}");
        }
    }

    private void calculateModel() {
        // TODO: Implement this method
    }

    public IRoomModelData getData() {
        return this.data;
    }
}
