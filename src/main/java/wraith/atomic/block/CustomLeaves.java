package wraith.atomic.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class CustomLeaves extends LeavesBlock {

    private final Block log;

    public CustomLeaves(Block log, Settings settings) {
        super(settings);
        this.log = log;
    }

    private static int getDistanceFromLog(BlockState state, Block log, Block leaf) {
        if (state.getBlock() == log) {
            return 0;
        } else {
            return state.getBlock() == leaf ? state.get(DISTANCE) : 7;
        }
    }

    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos, Block log, Block leaf) {
        int i = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            mutable.set(pos, direction);
            i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable), log, leaf) + 1);
            if (i == 1) {
                break;
            }
        }
        return state.with(DISTANCE, i);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return updateDistanceFromLogs(this.getDefaultState().with(PERSISTENT, true), ctx.getWorld(), ctx.getBlockPos(), log, this);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        int i = getDistanceFromLog(newState, log, this) + 1;
        if (i != 1 || state.get(DISTANCE) != i) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
        return state;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos, log, this), 3);
    }

}
