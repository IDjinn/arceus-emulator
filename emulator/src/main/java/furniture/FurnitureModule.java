package furniture;

import com.google.inject.AbstractModule;
import furniture.factory.FurnitureFactory;
import habbo.furniture.IFurnitureFactory;
import habbo.furniture.IFurnitureManager;

public class FurnitureModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IFurnitureManager.class).to(FurnitureManager.class);
        bind(IFurnitureFactory.class).to(FurnitureFactory.class);
        bind(IFurnitureManager.class).to(FurnitureManager.class);
    }
}
