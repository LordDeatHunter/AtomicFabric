package wraith.atomic.worldgen;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CustomSaplingGenerator extends SaplingGenerator {

    private final String fancyTree;
    private final String normalTree;

    public CustomSaplingGenerator(String fancyTree, String normalTree) {
        this.fancyTree = fancyTree;
        this.normalTree = normalTree;
    }

    @Override
    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        if (random.nextInt(10) == 0) {
            return CustomConfiguredFeatures.getTree(fancyTree);
        } else {
            return CustomConfiguredFeatures.getTree(normalTree);
        }
    }

}
