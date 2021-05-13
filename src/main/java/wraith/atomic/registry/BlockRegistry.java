package wraith.atomic.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import wraith.atomic.Utils;
import wraith.atomic.block.AtomicCompressorBlock;
import wraith.atomic.block.CustomLeaves;
import wraith.atomic.block.CustomSapling;
import wraith.atomic.worldgen.CustomSaplingGenerator;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {

    private static final HashMap<String, Block> BLOCKS = new HashMap<String, Block>() {{
        put("petrified_sapling", new CustomSapling(new CustomSaplingGenerator("petrified_tree", "fancy_petrified_tree"), AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).strength(1.5F, 6.0F).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
        put("petrified_log", new PillarBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));
        put("petrified_leaves", new CustomLeaves(get("petrified_log"), AbstractBlock.Settings.of(Material.LEAVES, MaterialColor.STONE).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning((BlockState state, BlockView world, BlockPos pos, EntityType<?> type) -> type == EntityType.OCELOT || type == EntityType.PARROT).suffocates((BlockState state, BlockView world, BlockPos pos) -> false).blockVision((BlockState state, BlockView world, BlockPos pos) -> false)));
        put("petrified_planks", new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));

        put("iron_sapling", new CustomSapling(new CustomSaplingGenerator("iron_tree", "fancy_iron_tree"), AbstractBlock.Settings.of(Material.METAL, MaterialColor.IRON).strength(1.5F, 6.0F).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
        put("iron_log", new PillarBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));
        put("iron_leaves", new CustomLeaves(get("iron_log"), AbstractBlock.Settings.of(Material.LEAVES, MaterialColor.IRON).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning((BlockState state, BlockView world, BlockPos pos, EntityType<?> type) -> type == EntityType.OCELOT || type == EntityType.PARROT).suffocates((BlockState state, BlockView world, BlockPos pos) -> false).blockVision((BlockState state, BlockView world, BlockPos pos) -> false)));
        put("iron_planks", new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.IRON).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));

        put("gold_sapling", new CustomSapling(new CustomSaplingGenerator("gold_tree", "fancy_gold_tree"), AbstractBlock.Settings.of(Material.METAL, MaterialColor.GOLD).strength(1.5F, 6.0F).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
        put("gold_log", new PillarBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.GOLD).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));
        put("gold_leaves", new CustomLeaves(get("gold_log"), AbstractBlock.Settings.of(Material.LEAVES, MaterialColor.GOLD).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning((BlockState state, BlockView world, BlockPos pos, EntityType<?> type) -> type == EntityType.OCELOT || type == EntityType.PARROT).suffocates((BlockState state, BlockView world, BlockPos pos) -> false).blockVision((BlockState state, BlockView world, BlockPos pos) -> false)));
        put("gold_planks", new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.GOLD).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));

        put("diamond_sapling", new CustomSapling(new CustomSaplingGenerator("diamond_tree", "fancy_diamond_tree"), AbstractBlock.Settings.of(Material.METAL, MaterialColor.DIAMOND).strength(1.5F, 6.0F).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
        put("diamond_log", new PillarBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));
        put("diamond_leaves", new CustomLeaves(get("diamond_log"), AbstractBlock.Settings.of(Material.LEAVES, MaterialColor.STONE).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning((BlockState state, BlockView world, BlockPos pos, EntityType<?> type) -> type == EntityType.OCELOT || type == EntityType.PARROT).suffocates((BlockState state, BlockView world, BlockPos pos) -> false).blockVision((BlockState state, BlockView world, BlockPos pos) -> false)));
        put("diamond_planks", new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));

        put("emerald_sapling", new CustomSapling(new CustomSaplingGenerator("emerald_tree", "fancy_emerald_tree"), AbstractBlock.Settings.of(Material.METAL, MaterialColor.EMERALD).strength(1.5F, 6.0F).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
        put("emerald_log", new PillarBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));
        put("emerald_leaves", new CustomLeaves(get("emerald_log"), AbstractBlock.Settings.of(Material.LEAVES, MaterialColor.STONE).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning((BlockState state, BlockView world, BlockPos pos, EntityType<?> type) -> type == EntityType.OCELOT || type == EntityType.PARROT).suffocates((BlockState state, BlockView world, BlockPos pos) -> false).blockVision((BlockState state, BlockView world, BlockPos pos) -> false)));
        put("emerald_planks", new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.WOOD)));

        put("compressed_obsidian", new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(75.0F, 1500.0F)));

        put("atomic_compressor", new AtomicCompressorBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.RED).requiresTool().strength(1.5F, 6.0F)));
    }};

    public static void registerBlocks() {
        for (Map.Entry<String, Block> entry : BLOCKS.entrySet()) {
            Registry.register(Registry.BLOCK, Utils.ID(entry.getKey()), entry.getValue());
        }
    }

    public static void registerTransparent() {
        BlockRenderLayerMap.INSTANCE.putBlock(BLOCKS.get("petrified_sapling"), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BLOCKS.get("iron_sapling"), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BLOCKS.get("gold_sapling"), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BLOCKS.get("diamond_sapling"), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BLOCKS.get("emerald_sapling"), RenderLayer.getCutout());
    }

    public static Block get(String id) {
        return BLOCKS.getOrDefault(id, Blocks.AIR);
    }

}
