package repositories;

import com.google.inject.AbstractModule;
import repositories.catalog.CatalogRepository;
import repositories.emulator.EmulatorRepository;
import repositories.furniture.FurnitureRepository;
import repositories.habbo.*;
import repositories.navigator.NavigatorRepository;
import repositories.rooms.RoomItemsRepository;
import repositories.rooms.RoomRepository;
import storage.repositories.catalog.ICatalogRepository;
import storage.repositories.emulator.IEmulatorRepository;
import storage.repositories.furniture.IFurnitureRepository;
import storage.repositories.habbo.*;
import storage.repositories.navigator.INavigatorRepository;
import storage.repositories.rooms.IRoomItemsRepository;
import storage.repositories.rooms.IRoomRepository;

public class RepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IHabboRepository.class).to(HabboRepository.class);
        bind(IEmulatorRepository.class).to(EmulatorRepository.class);
        bind(IFurnitureRepository.class).to(FurnitureRepository.class);
        bind(IRoomItemsRepository.class).to(RoomItemsRepository.class);
        bind(ICatalogRepository.class).to(CatalogRepository.class);
        bind(IHabboInventoryRepository.class).to(HabboInventoryRepository.class);
        bind(IRoomRepository.class).to(RoomRepository.class);
        bind(INavigatorRepository.class).to(NavigatorRepository.class);
        bind(IHabboSettingsRepository.class).to(HabboSettingsRepository.class);
        bind(IHabboNavigatorRepository.class).to(HabboNavigatorRepository.class);
        bind(IHabboRoomsRepository.class).to(HabboRoomsRepository.class);
        bind(IHabboWalletRepository.class).to(HabboWalletRepository.class);
    }
}
