package repositories;

import com.google.inject.AbstractModule;
import repositories.catalog.CatalogRepository;
import repositories.emulator.EmulatorRepository;
import repositories.furniture.FurnitureRepository;
import repositories.habbo.HabboRepository;
import repositories.rooms.RoomItemsRepository;
import storage.repositories.catalog.ICatalogRepository;
import storage.repositories.emulator.IEmulatorRepository;
import storage.repositories.furniture.IFurnitureRepository;
import storage.repositories.habbo.IHabboRepository;
import storage.repositories.rooms.IRoomItemsRepository;

public class RepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IHabboRepository.class).to(HabboRepository.class);
        bind(IEmulatorRepository.class).to(EmulatorRepository.class);
        bind(IFurnitureRepository.class).to(FurnitureRepository.class);
        bind(IRoomItemsRepository.class).to(RoomItemsRepository.class);
        bind(ICatalogRepository.class).to(CatalogRepository.class);
    }
}
