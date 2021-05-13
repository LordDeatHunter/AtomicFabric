package wraith.atomic.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class OutputSlot extends Slot {


    private final AtomicCombinerScreenHandler handler;

    public OutputSlot(Inventory inventory, int index, int x, int y, AtomicCombinerScreenHandler handler) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack takeStack(int amount) {
        if (this.hasStack()) {
            amount += Math.min(amount, this.getStack().getCount());
        }
        return super.takeStack(amount);
    }

    @Override
    public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
        handler.takeRecipeItems();
        return super.onTakeItem(player, stack);
    }

}
