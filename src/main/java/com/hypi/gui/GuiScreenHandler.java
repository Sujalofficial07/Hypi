package com.hypi.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;

public class GuiScreenHandler extends GenericContainerScreenHandler {

    public GuiScreenHandler(int syncId, PlayerInventory playerInv, Inventory inv) {
        super(ScreenHandlerType.GENERIC_9X6, syncId, playerInv, inv, 6);
    }

    @Override
    public void onSlotClick(int slot, int button, SlotActionType type, PlayerEntity player) {
        if (slot >= 0 && slot < 54) {
            if (!this.getSlot(slot).getStack().isEmpty())
                handleClick(slot, player);
            return;
        }
        super.onSlotClick(slot, button, type, player);
    }

    protected void handleClick(int slot, PlayerEntity player) {}

    @Override
    public boolean canUse(PlayerEntity player) { return true; }
}
