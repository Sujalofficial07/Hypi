package com.hypi.commands;

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

        // /hub command - sends player to hub (overworld spawn)
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
                    player.sendMessage(Text.literal("§aWelcome to the Hub!"), false);
                }
                return 1;
            })
        );

        // /is command - sends player to their island
        dispatcher.register(CommandManager.literal("is")
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player != null) {
                    ServerWorld island = player.getServer().getWorld(World.OVERWORLD);
                    // Island spawn will be updated once island dimension is added
                    player.sendMessage(Text.literal("§eTeleporting to your island..."), false);
                    player.teleport(island,
                        0.5, 65, 0.5,
                        player.getYaw(),
                        player.getPitch()
                    );
                    player.sendMessage(Text.literal("§aWelcome to your Island!"), false);
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
