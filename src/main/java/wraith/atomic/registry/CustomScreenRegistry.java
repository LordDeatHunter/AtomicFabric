package wraith.atomic.registry;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import wraith.atomic.screen.AtomicCombinerScreen;

public class CustomScreenRegistry {

    public static void registerScreens() {
        ScreenRegistry.register(CustomScreenHandlerRegistry.ATOMIC_COMBINER, AtomicCombinerScreen::new);
    }

}
