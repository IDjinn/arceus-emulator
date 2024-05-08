package repositories;

import com.google.inject.AbstractModule;
import repositories.catalog.CatalogRepository;
import repositories.emulator.EmulatorRepository;
import repositories.furniture.FurnitureRepository;
import repositories.habbo.*;
import repositories.messenger.MessengerRepository;
import repositories.navigator.NavigatorRepository;
import repositories.rooms.RoomItemsRepository;
import repositories.rooms.RoomRepository;
import storage.repositories.catalog.ICatalogRepository;
import storage.repositories.emulator.IEmulatorRepository;
import storage.repositories.furniture.IFurnitureRepository;
import storage.repositories.habbo.*;
import storage.repositories.messenger.IMessengerRepository;
import storage.repositories.navigator.INavigatorRepository;
import storage.repositories.rooms.IRoomItemsRepository;
import storage.repositories.rooms.IRoomRepository;

public class RepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(IHabboRepository.class).to(HabboRepository.class);
        this.bind(IEmulatorRepository.class).to(EmulatorRepository.class);
        this.bind(IFurnitureRepository.class).to(FurnitureRepository.class);
        this.bind(IRoomItemsRepository.class).to(RoomItemsRepository.class);
        this.bind(ICatalogRepository.class).to(CatalogRepository.class);
        this.bind(IHabboInventoryRepository.class).to(HabboInventoryRepository.class);
        this.bind(IRoomRepository.class).to(RoomRepository.class);
        this.bind(INavigatorRepository.class).to(NavigatorRepository.class);
        this.bind(IHabboSettingsRepository.class).to(HabboSettingsRepository.class);
        this.bind(IHabboNavigatorRepository.class).to(HabboNavigatorRepository.class);
        this.bind(IHabboRoomsRepository.class).to(HabboRoomsRepository.class);
        this.bind(IHabboWalletRepository.class).to(HabboWalletRepository.class);
        this.bind(IMessengerRepository.class).to(MessengerRepository.class);
    }
}
