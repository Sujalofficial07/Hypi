package com.hypi.gui;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.ItemStack;
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
                ItemStack menuItem = GuiHandler.makeGlowItem(
                    net.minecraft.item.Items.NETHER_STAR,
                    "§6§lSkyBlock Menu",
                    "§7", "§7Right-click to open the SkyBlock Menu!", "§7",
                    "§eRight-Click to open!"
                );
                // Slot 8 = last hotbar slot like Hypixel
                player.getInventory().setStack(8, menuItem);
                player.sendMessage(Text.literal(
                    "§aWelcome to §6§lHypI§a! " +
                    "Right-click the §6Nether Star §ain your hotbar!"
                ), false);
            }
        });
    }
}
