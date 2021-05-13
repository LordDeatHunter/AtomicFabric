package wraith.atomic.screen;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import wraith.atomic.Utils;
import wraith.atomic.item.AtomicCombinerItem;
import wraith.atomic.item.AtomicInventory;
import wraith.atomic.recipe.AtomicCombinerRecipe;
import wraith.atomic.recipe.AtomicRecipes;
import wraith.atomic.registry.CustomScreenHandlerRegistry;
import wraith.atomic.registry.ItemRegistry;

import java.util.Map;

public class AtomicCombinerScreenHandler extends ScreenHandler {


    private final Inventory inventory = new AtomicInventory(this, 9);
    private final ItemStack stack;
    private final PlayerEntity player;
    private AtomicCombinerRecipe recipe = null;

    public AtomicCombinerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new ItemStack(ItemRegistry.get("atomic_combiner")));
    }

    public AtomicCombinerScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack stack) {
        super(CustomScreenHandlerRegistry.ATOMIC_COMBINER, syncId);

        this.player = playerInventory.player;

        this.stack = stack;
        CompoundTag tag = this.stack.getOrCreateSubTag("Atomic");
        tag.putBoolean("isCurrent", true);

        if (stack.getItem() == ItemRegistry.get("atomic_combiner")) {
            DefaultedList<ItemStack> items = AtomicCombinerItem.getInventoryFromStack(stack);
            for (int i = 0; i < 9; ++i) {
                inventory.setStack(i, items.get(i));
            }
        }

        this.addSlot(new Slot(inventory, 0, 80, 18));
        this.addSlot(new Slot(inventory, 1, 112, 33));
        this.addSlot(new Slot(inventory, 2, 127, 65));
        this.addSlot(new Slot(inventory, 3, 112, 97));
        this.addSlot(new Slot(inventory, 4, 80, 112));
        this.addSlot(new Slot(inventory, 5, 48, 97));
        this.addSlot(new Slot(inventory, 6, 33, 65));
        this.addSlot(new Slot(inventory, 7, 48, 33));

        this.addSlot(new OutputSlot(inventory, 8, 80, 65, this));

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 146 + y * 18));
            }
        }
        for (int x = 0; x < 9; ++x) {
            this.addSlot(new LockedSlot(playerInventory, x, 8 + x * 18, 204, stack));
        }
        if(!this.player.getEntityWorld().isClient) {
            updateRecipe();
        }
    }

    @Override
    public void close(PlayerEntity player) {
        stack.getSubTag("Atomic").remove("isCurrent");
        Utils.saveInventoryToStack(stack, inventory, AtomicCombinerItem.SIZE);
        super.close(player);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getMainHandStack() == stack || player.getOffHandStack() == stack;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            int invSize = this.inventory.size();
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < invSize) {
                if (!this.insertItem(originalStack, invSize, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, invSize - 1, false)) {
                if (invSlot >= invSize && invSlot < this.slots.size() - 9) {
                    if (!this.insertItem(originalStack, this.slots.size() - 9, this.slots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (invSlot >= this.slots.size() - 9) {
                    if (!this.insertItem(originalStack, invSize, this.slots.size() - 9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, originalStack);
        }

        return newStack;

    }

    @Override
    public void onContentChanged(Inventory inventory) {

        if (player.getEntityWorld().isClient) {
            return;
        }

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) this.player;
        for (int i = 0; i < this.inventory.size() - 1; ++i) {
            serverPlayer.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(this.syncId, i, this.inventory.getStack(i)));
        }

        updateRecipe();
    }

    private void updateRecipe() {
        if (this.player.getEntityWorld().isClient) {
            return;
        }
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) this.player;
        this.recipe = AtomicRecipes.checkAtomicCombinerRecipe(this.inventory);
        if (this.recipe != null) {
            this.inventory.setStack(this.inventory.size() - 1, new ItemStack(Registry.ITEM.get(new Identifier(this.recipe.getOutputItem())), this.recipe.getOutputAmount()));
        } else {
            this.inventory.setStack(this.inventory.size() - 1, ItemStack.EMPTY);
        }

        Utils.saveInventoryToStack(stack, inventory, AtomicCombinerItem.SIZE);
        for (int i = this.inventory.size(); i < this.slots.size(); ++i) {
            serverPlayer.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(this.syncId, i, this.slots.get(i).getStack()));
        }
        serverPlayer.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(this.syncId, this.inventory.size() - 1, this.inventory.getStack(this.inventory.size() - 1)));
    }

    public void takeRecipeItems() {
        if (this.recipe == null) {
            return;
        }
        for (Map.Entry<String, Integer> inputItem : this.recipe.getInputItems().entrySet()) {
            for (int i = 0; i < this.inventory.size() - 1; ++i) {
                ItemStack stack = this.inventory.getStack(i);
                Identifier inventoryID = Registry.ITEM.getId(stack.getItem());
                if (inputItem.getKey().startsWith("#")) {
                    if(TagRegistry.item(new Identifier(inputItem.getKey().substring(1))).contains(stack.getItem())) {
                        stack.decrement(inputItem.getValue());
                        break;
                    }
                } else if (inventoryID.equals(new Identifier(inputItem.getKey()))) {
                    stack.decrement(inputItem.getValue());
                    break;
                }
            }
        }
        onContentChanged(this.inventory);
    }

}