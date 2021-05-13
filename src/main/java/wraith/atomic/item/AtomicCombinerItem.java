package wraith.atomic.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import wraith.atomic.Atomic;
import wraith.atomic.screen.AtomicCombinerScreenHandler;

public class AtomicCombinerItem extends Item {

    private static final Text TITLE = new TranslatableText("container." + Atomic.MOD_ID + ".atomic_combiner");
    public static final int SIZE = 9;

    public AtomicCombinerItem(Settings settings) {
        super(settings);
    }

    public static DefaultedList<ItemStack> getInventoryFromStack(ItemStack stack) {
        DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
        CompoundTag tag = stack.getSubTag("Atomic");
        if (tag == null) {
            return inventory;
        }
        Inventories.fromTag(tag, inventory);
        return inventory;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.openHandledScreen(createScreenHandlerFactory(user.getStackInHand(hand)));
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new AtomicCombinerScreenHandler(i, playerInventory, stack), TITLE);
    }

}
