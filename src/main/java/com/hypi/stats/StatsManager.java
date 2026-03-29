package com.hypi.stats;

import com.hypi.data.PlayerDataManager;
import com.hypi.data.PlayerDataManager.PlayerData;
import net.minecraft.server.network.ServerPlayerEntity;

public class StatsManager {

    // Stat types — used everywhere: items, armor, pets, accessories, potions
    public enum Stat {
        HEALTH, DEFENCE, SPEED, STRENGTH,
        CRIT_CHANCE, CRIT_DAMAGE, INTELLIGENCE,
        MAGIC_FIND, LUCK, FEROCITY
    }

    // Source of stat bonus — for future filtering/display
    public enum StatSource {
        ARMOR, ACCESSORY, PET, SKILL, POTION, ITEM, REFORGE
    }

    // Get final (base + bonus) stat value
    public static double get(ServerPlayerEntity player, Stat stat) {
        PlayerData d = PlayerDataManager.get(player);
        return switch (stat) {
            case HEALTH       -> d.getTotalHealth();
            case DEFENCE      -> d.getTotalDefence();
            case SPEED        -> d.getTotalSpeed();
            case STRENGTH     -> d.getTotalStrength();
            case CRIT_CHANCE  -> d.getTotalCritChance();
            case CRIT_DAMAGE  -> d.getTotalCritDamage();
            case INTELLIGENCE -> d.getTotalIntelligence();
            case MAGIC_FIND   -> d.getTotalMagicFind();
            case LUCK         -> d.getTotalLuck();
            case FEROCITY     -> d.getTotalFerocity();
        };
    }

    // Add bonus stat (from armor, pet, accessory, etc)
    public static void addBonus(ServerPlayerEntity player, Stat stat, double amount) {
        PlayerData d = PlayerDataManager.get(player);
        switch (stat) {
            case HEALTH       -> d.bonusHealth       += amount;
            case DEFENCE      -> d.bonusDefence      += amount;
            case SPEED        -> d.bonusSpeed        += amount;
            case STRENGTH     -> d.bonusStrength     += amount;
            case CRIT_CHANCE  -> d.bonusCritChance   += amount;
            case CRIT_DAMAGE  -> d.bonusCritDamage   += amount;
            case INTELLIGENCE -> d.bonusIntelligence += amount;
            case MAGIC_FIND   -> d.bonusMagicFind    += amount;
            case LUCK         -> d.bonusLuck         += amount;
            case FEROCITY     -> d.bonusFerocity     += amount;
        }
    }

    // Remove bonus stat (when armor removed, pet unequipped, etc)
    public static void removeBonus(ServerPlayerEntity player, Stat stat, double amount) {
        addBonus(player, stat, -amount);
    }

    // Reset all bonuses (recalculate from scratch)
    public static void resetBonuses(ServerPlayerEntity player) {
        PlayerData d = PlayerDataManager.get(player);
        d.bonusHealth = d.bonusDefence = d.bonusSpeed = d.bonusStrength = 0;
        d.bonusCritChance = d.bonusCritDamage = d.bonusIntelligence = 0;
        d.bonusMagicFind = d.bonusLuck = d.bonusFerocity = 0;
    }

    // Hypixel-style formatted stats string
    public static String formatAll(ServerPlayerEntity player) {
        PlayerData d = PlayerDataManager.get(player);
        return  "§c❤ Health: §f"        + fmt(d.getTotalHealth())       + "\n" +
                "§a❈ Defence: §f"       + fmt(d.getTotalDefence())      + "\n" +
                "§f✦ Speed: §f"         + fmt(d.getTotalSpeed())        + "\n" +
                "§c⚔ Strength: §f"      + fmt(d.getTotalStrength())     + "\n" +
                "§9☣ Crit Chance: §f"   + fmt(d.getTotalCritChance())   + "%\n" +
                "§9☠ Crit Damage: §f"   + fmt(d.getTotalCritDamage())   + "%\n" +
                "§b✎ Intelligence: §f"  + fmt(d.getTotalIntelligence()) + "\n" +
                "§d✯ Magic Find: §f"    + fmt(d.getTotalMagicFind())    + "\n" +
                "§e☘ Luck: §f"          + fmt(d.getTotalLuck())         + "\n" +
                "§c⫶ Ferocity: §f"      + fmt(d.getTotalFerocity());
    }

    public static String fmt(double val) {
        return val == (long) val
            ? String.valueOf((long) val)
            : String.format("%.1f", val);
    }
}
