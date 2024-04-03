package habbo.rooms;

import habbo.PipelineController;
import networking.packets.OutgoingPacket;

import java.util.LinkedList;
import java.util.List;

public class RoomPrepareJoinPipeline extends PipelineController<JoinEvent, List<OutgoingPacket>> {
    private static final List<OutgoingPacket> emptyResponse = new LinkedList<>();

    @Override
    public void init() {
        this.add("createEntity", joinEvent -> {
            joinEvent.getHabbo().setRoom(joinEvent.getRoom());
            var entity = joinEvent.getRoom().getEntitiesComponent().createHabboEntity(joinEvent.getHabbo());
            joinEvent.getHabbo().setPlayerEntity(entity);

            return emptyResponse;
        });


        this.add("success", joinEvent -> {
            if (joinEvent.getRoom() == null) {
                return emptyResponse;
            }
        });
    }


}
