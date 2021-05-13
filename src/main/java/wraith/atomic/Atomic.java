package wraith.atomic;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wraith.atomic.recipe.AtomicRecipes;
import wraith.atomic.registry.BlockRegistry;
import wraith.atomic.registry.CustomScreenHandlerRegistry;
import wraith.atomic.registry.ItemRegistry;

public class Atomic implements ModInitializer {

    public static final String MOD_ID = "atomic";
    public static final String MOD_NAME = "Atomic";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        Utils.saveFilesFromJar("configs/", "", false);

        AtomicRecipes.loadAllRecipes();

        ItemRegistry.registerItems();
        BlockRegistry.registerBlocks();
        BlockRegistry.registerTransparent();
        CustomScreenHandlerRegistry.registerScreenHandlers();
        LOGGER.info("[" + MOD_NAME + "] has been initiated.");
    }

}
