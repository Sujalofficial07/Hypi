package com.hypi.economy;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoinDropHandler {

    // Coin rewards per mob type
    private static final Map<Class<?>, Double> MOB_COINS = new HashMap<>();

    static {
        // Passive mobs
        MOB_COINS.put(CowEntity.class, 2.0);
        MOB_COINS.put(PigEntity.class, 2.0);
        MOB_COINS.put(SheepEntity.class, 2.0);
        MOB_COINS.put(ChickenEntity.class, 1.0);
        MOB_COINS.put(RabbitEntity.class, 1.0);

        // Hostile mobs
        MOB_COINS.put(ZombieEntity.class, 5.0);
        MOB_COINS.put(SkeletonEntity.class, 5.0);
        MOB_COINS.put(SpiderEntity.class, 4.0);
        MOB_COINS.put(CreeperEntity.class, 8.0);
        MOB_COINS.put(EndermanEntity.class, 15.0);
        MOB_COINS.put(WitchEntity.class, 12.0);
        MOB_COINS.put(BlazeEntity.class, 20.0);
        MOB_COINS.put(SlimeEntity.class, 3.0);
    }

    public static void register() {
        // Listen for entity death
        net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents.AFTER_DEATH.register(
            (entity, damageSource) -> {
                if (!(entity instanceof LivingEntity)) return;
                if (!(damageSource.getAttacker() instanceof ServerPlayerEntity player)) return;

                double coins = getCoinReward(entity);
                if (coins <= 0) return;

                // Add small random bonus (like Hypixel)
                double bonus = coins * (0.8 + Math.random() * 0.4);
                double finalCoins = Math.round(bonus * 10.0) / 10.0;

                EconomyData.addPurse(player, finalCoins);

                player.sendMessage(
                    Text.literal("§6+" + EconomyData.format(finalCoins) + " Coins"),
                    true
                );
            }
        );
    }

    private static double getCoinReward(LivingEntity entity) {
        for (Map.Entry<Class<?>, Double> entry : MOB_COINS.entrySet()) {
            if (entry.getKey().isInstance(entity)) {
                return entry.getValue();
            }
        }
        return 1.0; // Default 1 coin for unknown mobs
    }
}
