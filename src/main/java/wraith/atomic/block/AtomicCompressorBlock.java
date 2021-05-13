package wraith.atomic.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import wraith.atomic.recipe.AtomicRecipes;

import java.util.HashSet;

public class AtomicCompressorBlock extends FacingBlock {

    private static final VoxelShape GROUND = Block.createCuboidShape(1f, 0f, 1f, 15f, 10f, 15f).simplify();
    private static final VoxelShape CEILING = Block.createCuboidShape(1f, 6f, 1f, 15f, 16f, 15f).simplify();
    private static final VoxelShape SOUTH = Block.createCuboidShape(1f, 1f, 6f, 15f, 15f, 16f).simplify();
    private static final VoxelShape EAST = Block.createCuboidShape(6f, 1f, 1f, 16f, 15f, 15f).simplify();
    private static final VoxelShape NORTH = Block.createCuboidShape(1f, 1f, 0f, 15f, 15f, 10f).simplify();
    private static final VoxelShape WEST = Block.createCuboidShape(0f, 1f, 1f, 10f, 15f, 15f).simplify();

    public AtomicCompressorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case UP:
                return GROUND;
            case DOWN:
                return CEILING;
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                return NORTH;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getPlayerLookDirection().getOpposite();
        BlockPos pos = ctx.getBlockPos();
        HashSet<BlockPos> toRemove = new HashSet<>();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                BlockPos bpos;
                if (facing == Direction.DOWN || facing == Direction.UP) {
                    bpos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
                } else if (facing == Direction.WEST || facing == Direction.EAST) {
                    bpos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ() + j);
                } else {
                    bpos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ());
                }
                if (!ctx.getWorld().getBlockState(bpos).canReplace(ctx)) {
                    return null;
                } else {
                    toRemove.add(bpos);
                }
            }
        }
        for (BlockPos bpos : toRemove) {
            ctx.getWorld().breakBlock(bpos, true);
        }

        return this.getDefaultState().with(FACING, facing);
    }

    private static BlockPos goDown(Direction direction, BlockPos pos) {
        switch(direction) {
            case UP:
                return pos.down();
            case DOWN:
                return pos.up();
            case NORTH:
                return pos.south();
            case SOUTH:
                return pos.north();
            case EAST:
                return pos.west();
            default:
                return pos.east();
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Block block = null;
        int amount = 0;
        HashSet<BlockPos> positions = new HashSet<>();
        Direction facing = world.getBlockState(pos).get(FACING);
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                BlockPos bpos = goDown(facing, pos);
                if (facing == Direction.DOWN || facing == Direction.UP) {
                    bpos = new BlockPos(bpos.getX() + i, bpos.getY(), bpos.getZ() + j);
                } else if (facing == Direction.WEST || facing == Direction.EAST) {
                    bpos = new BlockPos(bpos.getX(), bpos.getY() + i, bpos.getZ() + j);
                } else {
                    bpos = new BlockPos(bpos.getX() + i, bpos.getY() + j, bpos.getZ());
                }
                Block current = world.getBlockState(bpos).getBlock();
                if (block != null && block != current) {
                    return ActionResult.FAIL;
                }
                positions.add(bpos);
                block = current;
                ++amount;
            }
        }
        String recipe = AtomicRecipes.getAtomicCompressorRecipe(Registry.BLOCK.getId(block).toString());
        if (amount != 9 || "".equals(recipe)) {
            return ActionResult.FAIL;
        }
        for (BlockPos bpos : positions) {
            world.breakBlock(bpos, false);
        }
        float x = 0;
        float y = 0;
        float z = 0;
        switch (facing) {
            case UP:
                y = 0.5f;
                break;
            case DOWN:
                y = -0.5f;
                break;
            case EAST:
                x = 0.5f;
                break;
            case WEST:
                x = -0.5f;
                break;
            case NORTH:
                z = 0.5f;
                break;
            case SOUTH:
                z = -0.5f;
                break;
        }
        ItemEntity item = new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(Registry.ITEM.get(new Identifier(recipe))));
        item.addVelocity(x, y, z);
        world.spawnEntity(item);
        return ActionResult.SUCCESS;
    }

}
