package com.hypi.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class GuiHandler {

    // Open a 9x6 chest GUI
    public static void openMenu(ServerPlayerEntity player, String title, SimpleInventory inv) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> new GuiScreenHandler(syncId, playerInv, inv),
            Text.literal(title)
        ));
    }

    // Build a display item with name + lore (like Hypixel)
    public static ItemStack makeItem(
            net.minecraft.item.Item material,
            String name,
            String... lore) {

        ItemStack stack = new ItemStack(material);
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = new NbtCompound();

        // Name
        display.putString("Name",
            Text.Serialization.toJsonString(Text.literal(name)));

        // Lore
        NbtList loreList = new NbtList();
        for (String line : lore) {
            loreList.add(NbtString.of(
                Text.Serialization.toJsonString(Text.literal(line))));
        }
        display.put("Lore", loreList);
        nbt.put("display", display);

        // Remove enchantment glint if needed
        nbt.putInt("HideFlags", 255);
        stack.setNbt(nbt);
        return stack;
    }

    // Make a glowing item (enchanted look)
    public static ItemStack makeGlowItem(
            net.minecraft.item.Item material,
            String name,
            String... lore) {

        ItemStack stack = makeItem(material, name, lore);
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtList enchants = new NbtList();
        NbtCompound ench = new NbtCompound();
        ench.putString("id", "minecraft:luck_of_the_sea");
        ench.putInt("lvl", 1);
        enchants.add(ench);
        nbt.put("Enchantments", enchants);
        nbt.putInt("HideFlags", 255);
        stack.setNbt(nbt);
        return stack;
    }

    // Gray stained glass pane filler (Hypixel border item)
    public static ItemStack filler() {
        return makeItem(Items.GRAY_STAINED_GLASS_PANE, "§8");
    }

    // Black stained glass pane filler
    public static ItemStack blackFiller() {
        return makeItem(Items.BLACK_STAINED_GLASS_PANE, "§8");
    }
}
