package wraith.atomic;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import wraith.atomic.registry.ItemRegistry;

public class CustomItemGroups {

    public static final ItemGroup ATOMIC = FabricItemGroupBuilder.create(Utils.ID("atomic")).icon(() -> new ItemStack(ItemRegistry.get("atomic_combiner"))).build();

}
