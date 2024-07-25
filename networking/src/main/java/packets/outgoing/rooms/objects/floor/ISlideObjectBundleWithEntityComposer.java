package packets.outgoing.rooms.objects.floor;

import networking.packets.outgoing.IOutgoingEvent;
import packets.outgoing.rooms.objects.floor.slide.EntitySlideObjectBundleDTO;

public interface ISlideObjectBundleWithEntityComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<EntitySlideObjectBundleDTO>{
}
