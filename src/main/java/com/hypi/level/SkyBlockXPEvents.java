package com.hypi.level;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class SkyBlockXPEvents {

    public static void register() {

        // Mob kill XP
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof LivingEntity
                    && source.getAttacker() instanceof ServerPlayerEntity player) {
                SkyBlockLevel.onMobKill(player);
            }
        });

        // Block break XP
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if (player instanceof ServerPlayerEntity sp) {
                SkyBlockLevel.onBlockMine(sp);
            }
        });
    }
}
