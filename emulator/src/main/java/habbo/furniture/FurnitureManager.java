package habbo.furniture;

import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.furniture.extra.data.*;
import io.netty.util.internal.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.repositories.furniture.IFurnitureRepository;
import utils.gson.GsonHelper;

import java.util.HashMap;

@Singleton
public class FurnitureManager implements IFurnitureManager {
    private final IFurnitureRepository furnitureRepository;
    private final HashMap<Integer, IFurniture> furnitures;
    private final IFurnitureFactory furnitureFactory;
    private final Logger logger = LogManager.getLogger(FurnitureManager.class);

    private final HashMap<Integer, Class<? extends ExtraData>> extraDataParsers;

    @Inject
    public FurnitureManager(IFurnitureRepository furnitureRepository, IFurnitureFactory furnitureFactory) {
        this.furnitureRepository = furnitureRepository;
        this.furnitureFactory = furnitureFactory;
        this.extraDataParsers = new HashMap<>(ExtraDataType.values().length);
        this.furnitures = new HashMap<>(10_000);
    }

    @Override
    public void init() {
        logger.info("Initializing furniture's from database...");
        this.extraDataParsers.put(ExtraDataType.Empty.getType(), EmptyExtraData.class);
        this.extraDataParsers.put(ExtraDataType.Legacy.getType(), LegacyExtraData.class);
        this.extraDataParsers.put(ExtraDataType.Map.getType(), MapExtraData.class);
        this.extraDataParsers.put(ExtraDataType.Number.getType(), NumberExtraData.class);

        this.furnitureRepository.getAllFurniture(result -> {
            if (result == null) return;

            var furniture = this.furnitureFactory.create(result);
            this.furnitures.put(furniture.getId(), furniture);
        });

        logger.info(
                "Loaded {} furniture from database.",
                this.furnitures.size()
        );
    }

    @Override
    public void destroy() {
        this.furnitures.values().forEach(IFurniture::destroy);
        this.furnitures.clear();
    }

    @Override
    public HashMap<Integer, IFurniture> getAll() {
        return this.furnitures;
    }

    @Override
    public @Nullable IFurniture get(int id) {
        return this.furnitures.get(id);
    }

    @Override
    public IExtraData parseExtraData(String json) {
        if (StringUtil.isNullOrEmpty(json))
            return new LegacyExtraData("");

        try {
            var extraDataType = GsonHelper.getGson()
                    .fromJson(json, ExtraData.ExtraDataReader.class)
                    .type;

            if (this.extraDataParsers.containsKey(extraDataType)) {
                return GsonHelper.getGson()
                        .fromJson(json, this.extraDataParsers.get(extraDataType));
            }

            return GsonHelper.getGson().fromJson(json, LegacyExtraData.class);

        } catch (JsonSyntaxException _) {
            // fall through to fallback
        } catch (Exception e) {
            logger.warn(
                    "Failed to parse extra data: {}",
                    json,
                    e
            );
        }

        return LegacyExtraData.fromLegacyString(json);
    }
}