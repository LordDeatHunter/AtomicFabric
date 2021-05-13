package wraith.atomic.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import wraith.atomic.Utils;
import wraith.atomic.screen.AtomicCombinerScreenHandler;

public class CustomScreenHandlerRegistry {
    public static ScreenHandlerType<? extends ScreenHandler> ATOMIC_COMBINER;

    public static void registerScreenHandlers() {
        ATOMIC_COMBINER = ScreenHandlerRegistry.registerSimple(Utils.ID("waystone"), AtomicCombinerScreenHandler::new);
    }
}
