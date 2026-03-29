package com.hypi.commands;

import com.hypi.world.IslandManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IslandCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        // /hub command
        dispatcher.register(CommandManager.literal("hub")
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player != null) {
                    ServerWorld hub = player.getServer().getWorld(World.OVERWORLD);
                    BlockPos spawnPos = hub.getSpawnPos();
                    player.teleport(hub,
                        spawnPos.getX() + 0.5,
                        spawnPos.getY(),
                        spawnPos.getZ() + 0.5,
                        player.getYaw(),
                        player.getPitch()
                    );
                    player.sendMessage(Text.literal("§aWelcome back to the Hub!"), false);
                }
                return 1;
            })
        );

        // /is command - now uses IslandManager
        dispatcher.register(CommandManager.literal("is")
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player != null) {
                    IslandManager.teleportToIsland(player);
                }
                return 1;
            })
        );

        // /spawn command
        dispatcher.register(CommandManager.literal("spawn")
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player != null) {
                    ServerWorld hub = player.getServer().getWorld(World.OVERWORLD);
                    BlockPos spawnPos = hub.getSpawnPos();
                    player.teleport(hub,
                        spawnPos.getX() + 0.5,
                        spawnPos.getY(),
                        spawnPos.getZ() + 0.5,
                        player.getYaw(),
                        player.getPitch()
                    );
                    player.sendMessage(Text.literal("§aTeleported to spawn!"), false);
                }
                return 1;
            })
        );
    }
}
