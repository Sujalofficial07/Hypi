package com.hypi.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;

public class GuiScreenHandler extends GenericContainerScreenHandler {

    public GuiScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenHandlerType.GENERIC_9X6, syncId, playerInventory, inventory, 6);
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        // Block all item movement in GUI — Hypixel style
        if (slotIndex < 54) {
            ItemStack stack = this.getSlot(slotIndex).getStack();
            if (!stack.isEmpty()) {
                handleClick(slotIndex, player);
            }
            return;
        }
        super.onSlotClick(slotIndex, button, actionType, player);
    }

    // Override in specific menus via callback
    protected void handleClick(int slot, PlayerEntity player) {}

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
