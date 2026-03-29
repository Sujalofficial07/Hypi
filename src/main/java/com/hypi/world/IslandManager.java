package com.hypi.world;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IslandManager {

    // Tracks which players already have an island generated
    private static final Set<UUID> generatedIslands = new HashSet<>();

    // Island spawn point (center of starter island)
    public static final BlockPos ISLAND_SPAWN = new BlockPos(0, 65, 0);

    public static void teleportToIsland(ServerPlayerEntity player) {
        ServerWorld islandWorld = player.getServer().getWorld(IslandDimension.ISLAND_WORLD_KEY);

        if (islandWorld == null) {
            player.sendMessage(Text.literal("§cIsland world not found! Report this bug."), false);
            return;
        }

        // Generate island if first time
        if (!generatedIslands.contains(player.getUuid())) {
            player.sendMessage(Text.literal("§eGenerating your island..."), false);
            IslandGenerator.generateStarterIsland(islandWorld, ISLAND_SPAWN);
            generatedIslands.add(player.getUuid());
            player.sendMessage(Text.literal("§aYour island has been created!"), false);
        }

        // Teleport player to island
        player.teleport(
            islandWorld,
            ISLAND_SPAWN.getX() + 0.5,
            ISLAND_SPAWN.getY() + 2,
            ISLAND_SPAWN.getZ() + 0.5,
            player.getYaw(),
            player.getPitch()
        );

        player.sendMessage(Text.literal("§aWelcome to your Island! §7Use §f/hub §7to return."), false);
    }
}
