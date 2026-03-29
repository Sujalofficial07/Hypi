package com.hypi.economy;

import com.hypi.data.PlayerDataManager;
import net.minecraft.server.network.ServerPlayerEntity;

public class EconomyData {

    public static double getPurse(ServerPlayerEntity p) {
        return PlayerDataManager.get(p).purse;
    }

    public static void setPurse(ServerPlayerEntity p, double v) {
        PlayerDataManager.get(p).purse = Math.max(0, v);
    }

    public static void addPurse(ServerPlayerEntity p, double v) {
        setPurse(p, getPurse(p) + v);
    }

    public static boolean takePurse(ServerPlayerEntity p, double v) {
        if (getPurse(p) < v) return false;
        setPurse(p, getPurse(p) - v);
        return true;
    }

    public static double getBank(ServerPlayerEntity p) {
        return PlayerDataManager.get(p).bank;
    }

    public static void setBank(ServerPlayerEntity p, double v) {
        PlayerDataManager.get(p).bank = Math.max(0, v);
    }

    public static void addBank(ServerPlayerEntity p, double v) {
        setBank(p, getBank(p) + v);
    }

    public static boolean takeBank(ServerPlayerEntity p, double v) {
        if (getBank(p) < v) return false;
        setBank(p, getBank(p) - v);
        return true;
    }

    public static String format(double amount) {
        if (amount >= 1_000_000_000) return String.format("%.1fB", amount / 1_000_000_000);
        if (amount >= 1_000_000)     return String.format("%.1fM", amount / 1_000_000);
        if (amount >= 1_000)         return String.format("%.1fk", amount / 1_000);
        return String.format("%.1f", amount);
    }
}
