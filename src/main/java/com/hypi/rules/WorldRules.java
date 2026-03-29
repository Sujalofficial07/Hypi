package com.hypi.rules;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldRules {

    // Hub world dimension key (overworld is used as hub)
    public static final String HUB_DIMENSION = "minecraft:overworld";
    // Island world dimension key
    public static final String ISLAND_DIMENSION = "hypi:island_world";

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
        return world.getRegistryKey().getValue().toString().equals(HUB_DIMENSION);
    }

    public static boolean isIslandWorld(World world) {
        return world.getRegistryKey().getValue().toString().equals(ISLAND_DIMENSION);
    }
}
