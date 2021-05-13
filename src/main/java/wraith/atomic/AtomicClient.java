package wraith.atomic;

import net.fabricmc.api.ClientModInitializer;
import wraith.atomic.registry.CustomScreenRegistry;

public class AtomicClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CustomScreenRegistry.registerScreens();
    }

}
