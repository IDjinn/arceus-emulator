package habbo.rooms;

import core.IPipeline;
import habbo.PipelineController;
import networking.packets.OutgoingPacket;
import packets.outgoing.rooms.prepare.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RoomPrepareJoinPipeline extends PipelineController<JoinEvent, List<OutgoingPacket>> {
    private static final List<OutgoingPacket> emptyResponse = new LinkedList<>();

    @Override
    public void init() {
        this.addFirst(new CreateHabboEntityProcess());
        this.addLast(new PacketsToSendProcess());
    }

    private static class CreateHabboEntityProcess implements IPipeline<JoinEvent, List<OutgoingPacket>> {
        @Override
        public String getName() {
            return CreateHabboEntityProcess.class.getName();
        }

        @Override
        public List<OutgoingPacket> process(JoinEvent joinEvent) {
            joinEvent.habbo().setRoom(joinEvent.room());
            var entity = joinEvent.room().getEntitiesComponent().createHabboEntity(joinEvent.habbo());
            joinEvent.habbo().setPlayerEntity(entity);

            return emptyResponse;
        }
    }

    private static class PacketsToSendProcess implements IPipeline<JoinEvent, List<OutgoingPacket>> {
        @Override
        public String getName() {
            return PacketsToSendProcess.class.getName();
        }

        @Override
        public List<OutgoingPacket> process(JoinEvent joinEvent) {
            joinEvent.habbo().setRoom(joinEvent.room());
            var entity = joinEvent.room().getEntitiesComponent().createHabboEntity(joinEvent.habbo());
            joinEvent.habbo().setPlayerEntity(entity);
            var toSend = new ArrayList<OutgoingPacket>(10);
            toSend.add(new HideDoorbellComposer());
            toSend.add(new RoomOpenComposer());
            toSend.add(new RoomDataComposer(joinEvent.room(), joinEvent.habbo(), false, true));
            toSend.add(new RoomModelComposer("model_a", joinEvent.room().getId()));
            toSend.add(new RoomPaintComposer("landscape", "0.0"));
            toSend.add(new RoomRightsComposer(0));
            toSend.add(new RoomScoreComposer(0, true));
            toSend.add(new RoomPromotionMessageComposer());
            toSend.add(new RoomRelativeMapComposer(joinEvent.room().getGameMap()));
            toSend.add(new RoomHeightMapComposer(joinEvent.room().getGameMap()));
            toSend.add(new RoomFloorItemsComposer(joinEvent.room().getObjectManager().getFurnitureOwners(), joinEvent.room().getObjectManager().getAllFloorItems()));
            toSend.add(new RoomWallItemsComposer(joinEvent.room().getObjectManager().getFurnitureOwners(), joinEvent.room().getObjectManager().getAllWallItems()));

            return toSend;
        }
    }


}
