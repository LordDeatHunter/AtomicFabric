package wraith.atomic.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import wraith.atomic.CustomItemGroups;
import wraith.atomic.Utils;
import wraith.atomic.item.AtomicCombinerItem;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {

    private static final HashMap<String, Item> ITEMS = new HashMap<String, Item>() {{
        put("atomic_combiner", new AtomicCombinerItem(new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("atomic_compressor", new BlockItem(BlockRegistry.get("atomic_compressor"), new Item.Settings().group(CustomItemGroups.ATOMIC)));

        put("petrified_sapling", new BlockItem(BlockRegistry.get("petrified_sapling"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("petrified_log", new BlockItem(BlockRegistry.get("petrified_log"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("petrified_leaves", new BlockItem(BlockRegistry.get("petrified_leaves"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("petrified_planks", new BlockItem(BlockRegistry.get("petrified_planks"), new Item.Settings().group(CustomItemGroups.ATOMIC)));

        put("iron_sapling", new BlockItem(BlockRegistry.get("iron_sapling"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("iron_log", new BlockItem(BlockRegistry.get("iron_log"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("iron_leaves", new BlockItem(BlockRegistry.get("iron_leaves"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("iron_planks", new BlockItem(BlockRegistry.get("iron_planks"), new Item.Settings().group(CustomItemGroups.ATOMIC)));

        put("gold_sapling", new BlockItem(BlockRegistry.get("gold_sapling"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("gold_log", new BlockItem(BlockRegistry.get("gold_log"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("gold_leaves", new BlockItem(BlockRegistry.get("gold_leaves"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("gold_planks", new BlockItem(BlockRegistry.get("gold_planks"), new Item.Settings().group(CustomItemGroups.ATOMIC)));

        put("diamond_sapling", new BlockItem(BlockRegistry.get("diamond_sapling"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("diamond_log", new BlockItem(BlockRegistry.get("diamond_log"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("diamond_leaves", new BlockItem(BlockRegistry.get("diamond_leaves"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("diamond_planks", new BlockItem(BlockRegistry.get("diamond_planks"), new Item.Settings().group(CustomItemGroups.ATOMIC)));

        put("emerald_sapling", new BlockItem(BlockRegistry.get("emerald_sapling"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("emerald_log", new BlockItem(BlockRegistry.get("emerald_log"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("emerald_leaves", new BlockItem(BlockRegistry.get("emerald_leaves"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
        put("emerald_planks", new BlockItem(BlockRegistry.get("emerald_planks"), new Item.Settings().group(CustomItemGroups.ATOMIC)));

        put("compressed_obsidian", new BlockItem(BlockRegistry.get("compressed_obsidian"), new Item.Settings().group(CustomItemGroups.ATOMIC)));
    }};

    public static void registerItems() {
        for (Map.Entry<String, Item> entry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, Utils.ID(entry.getKey()), entry.getValue());
        }
    }

    public static Item get(String id) {
        return ITEMS.getOrDefault(id, Items.AIR);
    }

}
