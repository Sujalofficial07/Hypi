package com.hypi.level;

import com.hypi.data.PlayerDataManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SkyBlockLevel {

    private static final int[] XP_TABLE = new int[500];

    static {
        for (int i = 0; i < 500; i++) {
            if      (i < 100) XP_TABLE[i] = 100  + (i * 75);
            else if (i < 200) XP_TABLE[i] = 7600 + ((i - 100) * 150);
            else if (i < 300) XP_TABLE[i] = 22600 + ((i - 200) * 300);
            else              XP_TABLE[i] = 52600 + ((i - 300) * 600);
        }
    }

    public static int getLevel(ServerPlayerEntity player) {
        double xp = PlayerDataManager.get(player).skyBlockXP;
        int level = 0;
        while (level < 499 && xp >= XP_TABLE[level]) {
            xp -= XP_TABLE[level];
            level++;
        }
        return level;
    }

    public static double getXPInLevel(ServerPlayerEntity player) {
        double xp = PlayerDataManager.get(player).skyBlockXP;
        int level = 0;
        while (level < 499 && xp >= XP_TABLE[level]) {
            xp -= XP_TABLE[level];
            level++;
        }
        return xp;
    }

    public static int getXPForNext(int level) {
        return level >= 499 ? 0 : XP_TABLE[level];
    }

    public static void addXP(ServerPlayerEntity player, double amount) {
        PlayerDataManager.PlayerData data = PlayerDataManager.get(player);
        int before = getLevel(player);
        data.skyBlockXP += amount;
        int after = getLevel(player);
        if (after > before) {
            player.sendMessage(Text.literal(
                "\n§6§l  ★ SKYBLOCK LEVEL UP §e" + before + " §6➜ §e" + after + " §6★\n"
            ), false);
        }
    }

    // Called from events
    public static void onMobKill(ServerPlayerEntity p)   { addXP(p, 5);  }
    public static void onBlockMine(ServerPlayerEntity p) { addXP(p, 1);  }
    public static void onCraft(ServerPlayerEntity p)     { addXP(p, 2);  }
    public static void onFarm(ServerPlayerEntity p)      { addXP(p, 1);  }
    public static void onFish(ServerPlayerEntity p)      { addXP(p, 3);  }

    public static String getProgressBar(ServerPlayerEntity player) {
        int level = getLevel(player);
        if (level >= 499) return "§6§lMAX LEVEL";
        double cur    = getXPInLevel(player);
        double needed = getXPForNext(level);
        int filled    = (int)((cur / needed) * 20);
        StringBuilder bar = new StringBuilder("§8[");
        for (int i = 0; i < 20; i++)
            bar.append(i < filled ? "§e§l-" : "§7-");
        bar.append("§8] §e").append(String.format("%.0f", cur))
           .append("§7/§e").append(String.format("%.0f", needed)).append(" XP");
        return bar.toString();
    }
}
