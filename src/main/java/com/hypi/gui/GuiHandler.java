package com.hypi.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

public class GuiHandler {

    public static ItemStack makeItem(net.minecraft.item.Item mat, String name, String... lore) {
        ItemStack stack = new ItemStack(mat);
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = new NbtCompound();
        display.putString("Name",
            Text.Serialization.toJsonString(Text.literal(name)));
        NbtList loreList = new NbtList();
        for (String line : lore)
            loreList.add(NbtString.of(
                Text.Serialization.toJsonString(Text.literal(line))));
        display.put("Lore", loreList);
        nbt.put("display", display);
        nbt.putInt("HideFlags", 255);
        stack.setNbt(nbt);
        return stack;
    }

    public static ItemStack makeGlowItem(net.minecraft.item.Item mat, String name, String... lore) {
        ItemStack stack = makeItem(mat, name, lore);
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

    public static ItemStack filler() {
        return makeItem(Items.GRAY_STAINED_GLASS_PANE, "§8");
    }
}
