package wraith.atomic.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.slot.Slot;
import wraith.atomic.registry.ItemRegistry;

public class LockedSlot extends Slot {

    private final ItemStack stack;
    private final int index;

    public LockedSlot(Inventory inventory, int index, int x, int y, ItemStack stack) {
        super(inventory, index, x, y);
        this.index = index;
        this.stack = stack;
    }

    private boolean isLockedStack() {
        if (this.inventory.getStack(index) != stack && this.inventory.getStack(index).getItem() != ItemRegistry.get("atomic_combiner")) {
            return false;
        }
        CompoundTag tag = stack.getSubTag("Atomic");
        if (tag == null) {
            return false;
        }
        return tag.contains("isCurrent") && tag.getBoolean("isCurrent");
    }

    @Override
    public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
        return isLockedStack() ? ItemStack.EMPTY : super.onTakeItem(player, stack);
    }

    @Override
    public ItemStack takeStack(int amount) {
        return isLockedStack() ? ItemStack.EMPTY : super.takeStack(amount);
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return !isLockedStack();
    }

}
