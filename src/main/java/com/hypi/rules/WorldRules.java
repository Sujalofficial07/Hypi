package com.hypi.rules;

import com.hypi.world.IslandDimension;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class WorldRules {

    public static void register() {

        // Block breaking rule
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (isHubWorld(world) && !player.isCreative()) {
                player.sendMessage(
                    net.minecraft.text.Text.literal("§cYou cannot break blocks on the Hub!"),
                    true
                );
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        // Block placing rule
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (isHubWorld(world) && !player.isCreative()) {
                player.sendMessage(
                    net.minecraft.text.Text.literal("§cYou cannot place blocks on the Hub!"),
                    true
                );
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }

    public static boolean isHubWorld(World world) {
        return world.getRegistryKey().equals(World.OVERWORLD);
    }

    public static boolean isIslandWorld(World world) {
        return world.getRegistryKey().equals(IslandDimension.ISLAND_WORLD_KEY);
    }
}
