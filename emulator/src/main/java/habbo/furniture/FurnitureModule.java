package habbo.furniture;

import com.google.inject.AbstractModule;
import habbo.furniture.factory.FurnitureFactory;

public class FurnitureModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(IFurnitureManager.class).to(FurnitureManager.class);
        this.bind(IFurnitureFactory.class).to(FurnitureFactory.class);
        this.bind(IFurnitureManager.class).to(FurnitureManager.class);
    }
}
