package wraith.atomic.worldgen;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import wraith.atomic.registry.BlockRegistry;

import java.util.HashMap;
import java.util.OptionalInt;

public class CustomConfiguredFeatures {

    private static final HashMap<String, ConfiguredFeature<TreeFeatureConfig, ?>> TREES = new HashMap<String, ConfiguredFeature<TreeFeatureConfig, ?>>() {{

        put("petrified_tree", register("petrified_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("petrified_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("petrified_leaves").getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build())));
        put("fancy_petrified_tree", register("fancy_petrified_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("petrified_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("petrified_leaves").getDefaultState()), new LargeOakFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(4), 4), new LargeOakTrunkPlacer(3, 11, 0), new TwoLayersFeatureSize(0, 0, 0,OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build())));

        put("iron_tree", register("iron_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("iron_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("iron_leaves").getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build())));
        put("fancy_iron_tree", register("fancy_iron_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("iron_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("iron_leaves").getDefaultState()), new LargeOakFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(4), 4), new LargeOakTrunkPlacer(3, 11, 0), new TwoLayersFeatureSize(0, 0, 0,OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build())));

        put("gold_tree", register("gold_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("gold_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("gold_leaves").getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build())));
        put("fancy_gold_tree", register("fancy_gold_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("gold_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("gold_leaves").getDefaultState()), new LargeOakFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(4), 4), new LargeOakTrunkPlacer(3, 11, 0), new TwoLayersFeatureSize(0, 0, 0,OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build())));

        put("diamond_tree", register("diamond_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("diamond_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("diamond_leaves").getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build())));
        put("fancy_diamond_tree", register("fancy_diamond_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("diamond_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("diamond_leaves").getDefaultState()), new LargeOakFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(4), 4), new LargeOakTrunkPlacer(3, 11, 0), new TwoLayersFeatureSize(0, 0, 0,OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build())));

        put("emerald_tree", register("emerald_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("emerald_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("emerald_leaves").getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build())));
        put("fancy_emerald_tree", register("fancy_emerald_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.get("emerald_log").getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.get("emerald_leaves").getDefaultState()), new LargeOakFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(4), 4), new LargeOakTrunkPlacer(3, 11, 0), new TwoLayersFeatureSize(0, 0, 0,OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build())));

    }};

    public static ConfiguredFeature<TreeFeatureConfig, ?> getTree(String tree) {
        return TREES.get(tree);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

}
