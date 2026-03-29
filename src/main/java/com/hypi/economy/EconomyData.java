package com.hypi.economy;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyData {

    // Stores purse (coins on hand) per player
    private static final Map<UUID, Double> purses = new HashMap<>();
    // Stores bank balance per player
    private static final Map<UUID, Double> banks = new HashMap<>();

    // === PURSE ===
    public static double getPurse(ServerPlayerEntity player) {
        return purses.getOrDefault(player.getUuid(), 0.0);
    }

    public static void setPurse(ServerPlayerEntity player, double amount) {
        purses.put(player.getUuid(), Math.max(0, amount));
    }

    public static void addPurse(ServerPlayerEntity player, double amount) {
        setPurse(player, getPurse(player) + amount);
    }

    public static boolean takePurse(ServerPlayerEntity player, double amount) {
        if (getPurse(player) < amount) return false;
        setPurse(player, getPurse(player) - amount);
        return true;
    }

    // === BANK ===
    public static double getBank(ServerPlayerEntity player) {
        return banks.getOrDefault(player.getUuid(), 0.0);
    }

    public static void setBank(ServerPlayerEntity player, double amount) {
        banks.put(player.getUuid(), Math.max(0, amount));
    }

    public static void addBank(ServerPlayerEntity player, double amount) {
        setBank(player, getBank(player) + amount);
    }

    public static boolean takeBank(ServerPlayerEntity player, double amount) {
        if (getBank(player) < amount) return false;
        setBank(player, getBank(player) - amount);
        return true;
    }

    // === FORMATTING (like Hypixel: 1,234.5) ===
    public static String format(double amount) {
        if (amount >= 1_000_000_000)
            return String.format("%.1fB", amount / 1_000_000_000);
        if (amount >= 1_000_000)
            return String.format("%.1fM", amount / 1_000_000);
        if (amount >= 1_000)
            return String.format("%.1fk", amount / 1_000);
        return String.format("%.1f", amount);
    }
}
