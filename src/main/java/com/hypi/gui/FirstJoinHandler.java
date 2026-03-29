package com.hypi.gui;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FirstJoinHandler {

    private static final Set<UUID> joined = new HashSet<>();

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            if (!joined.contains(player.getUuid())) {
                joined.add(player.getUuid());

                // Give Nether Star (SkyBlock Menu item)
                ItemStack menuItem = GuiHandler.makeGlowItem(
                    Items.NETHER_STAR,
                    "§6§lSkyBlock Menu",
                    "§7",
                    "§7Right-click to open the",
                    "§7SkyBlock Menu!",
                    "§7",
                    "§eRight-Click to open!"
                );

                // Put in hotbar slot 8 (last slot, like Hypixel)
                player.getInventory().setStack(8, menuItem);
                player.sendMessage(Text.literal(
                    "§aWelcome to §6§lHypI§a! Use the §6Nether Star §ain your hotbar to open the SkyBlock Menu."
                ), false);
            }
        });
    }
}
