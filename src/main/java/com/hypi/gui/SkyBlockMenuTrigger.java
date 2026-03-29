package com.hypi.gui;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.TypedActionResult;

public class SkyBlockMenuTrigger {

    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!world.isClient()
                    && player instanceof ServerPlayerEntity sp
                    && player.getStackInHand(hand).isOf(Items.NETHER_STAR)) {
                SkyBlockMenu.open(sp);
                return TypedActionResult.success(player.getStackInHand(hand));
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });
    }
}
