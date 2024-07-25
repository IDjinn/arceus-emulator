package packets.outgoing.rooms.objects.floor;

import networking.packets.outgoing.IOutgoingEvent;
import packets.outgoing.rooms.objects.floor.slide.EntitySlideObjectBundleDTO;
import packets.outgoing.rooms.objects.floor.slide.SlideObjectEntry;

public interface ISlideObjectBundleWithEntityComposer extends IOutgoingEvent<EntitySlideObjectBundleDTO> {
}
