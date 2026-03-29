package com.hypi.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

public class GuiHandler {

    // Convert Text to JSON string — 1.20.1 compatible
    private static String toJson(String text) {
        return Text.Serializer.toJson(Text.literal(text));
    }

    public static ItemStack makeItem(net.minecraft.item.Item mat, String name, String... lore) {
        ItemStack stack = new ItemStack(mat);
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = new NbtCompound();

        display.putString("Name", toJson(name));

        NbtList loreList = new NbtList();
        for (String line : lore)
            loreList.add(NbtString.of(toJson(line)));
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
