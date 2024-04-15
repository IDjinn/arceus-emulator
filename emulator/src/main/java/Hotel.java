import com.google.inject.Inject;
import core.IHotel;
import core.concurrency.IProcessHandler;
import habbo.catalog.ICatalogManager;
import habbo.furniture.IFurnitureManager;
import habbo.navigator.INavigatorManager;
import habbo.rooms.IRoomManager;

public class Hotel implements IHotel {
    private final IProcessHandler processHandler;
    private final IFurnitureManager furnitureManager;
    private final ICatalogManager catalogManager;
    private final IRoomManager roomManager;
    private final INavigatorManager navigatorManager;

    @Inject
    public Hotel(IProcessHandler processHandler, IFurnitureManager furnitureManager, ICatalogManager catalogManager, IRoomManager roomManager, INavigatorManager navigatorManager) {
        this.processHandler = processHandler;
        this.furnitureManager = furnitureManager;
        this.catalogManager = catalogManager;
        this.roomManager = roomManager;
        this.navigatorManager = navigatorManager;
    }

    @Override
    public IProcessHandler getProcessHandler() {
        return this.processHandler;
    }

    @Override
    public void init() {
        this.furnitureManager.init();
        this.catalogManager.init();
        this.roomManager.init();
        this.navigatorManager.init();
    }

    @Override
    public void destroy() {
        this.furnitureManager.destroy();
        this.catalogManager.destroy();
        this.roomManager.destroy();
        this.navigatorManager.destroy();
    }
}
