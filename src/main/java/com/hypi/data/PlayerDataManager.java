package com.hypi.data;

import net.minecraft.server.network.ServerPlayerEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private static final Map<UUID, PlayerData> dataMap = new HashMap<>();

    public static PlayerData get(ServerPlayerEntity player) {
        return dataMap.computeIfAbsent(player.getUuid(), id -> new PlayerData());
    }

    public static class PlayerData {

        // === SKYBLOCK LEVEL ===
        public double skyBlockXP = 0;

        // === BASE STATS (used by StatsManager, items, armor, pets) ===
        public double baseHealth        = 100;
        public double baseDefence       = 0;
        public double baseSpeed         = 100;
        public double baseStrength      = 0;
        public double baseCritChance    = 20;
        public double baseCritDamage    = 50;
        public double baseIntelligence  = 0;
        public double baseMagicFind     = 0;
        public double baseLuck          = 0;
        public double baseFerocity      = 0;

        // === BONUS STATS (added by armor, accessories, pets, skills, potions) ===
        public double bonusHealth       = 0;
        public double bonusDefence      = 0;
        public double bonusSpeed        = 0;
        public double bonusStrength     = 0;
        public double bonusCritChance   = 0;
        public double bonusCritDamage   = 0;
        public double bonusIntelligence = 0;
        public double bonusMagicFind    = 0;
        public double bonusLuck         = 0;
        public double bonusFerocity     = 0;

        // === SKILLS XP ===
        public double farmingXP     = 0;
        public double miningXP      = 0;
        public double combatXP      = 0;
        public double foragingXP    = 0;
        public double fishingXP     = 0;
        public double enchantingXP  = 0;
        public double alchemyXP     = 0;
        public double carpentryXP   = 0;
        public double runecraftingXP = 0;
        public double socialXP      = 0;

        // === ECONOMY ===
        public double purse = 0;
        public double bank  = 0;

        // === COLLECTIONS (item name -> count) ===
        public Map<String, Long> collections = new HashMap<>();

        // === PROFILE ===
        public String profileName   = "Coconut";
        public long   joinTime      = System.currentTimeMillis();
        public int    fairySouls    = 0;

        // === HELPER: get total stat (base + bonus) ===
        public double getTotalHealth()       { return baseHealth       + bonusHealth;       }
        public double getTotalDefence()      { return baseDefence      + bonusDefence;      }
        public double getTotalSpeed()        { return baseSpeed        + bonusSpeed;        }
        public double getTotalStrength()     { return baseStrength     + bonusStrength;     }
        public double getTotalCritChance()   { return baseCritChance   + bonusCritChance;   }
        public double getTotalCritDamage()   { return baseCritDamage   + bonusCritDamage;   }
        public double getTotalIntelligence() { return baseIntelligence + bonusIntelligence; }
        public double getTotalMagicFind()    { return baseMagicFind    + bonusMagicFind;    }
        public double getTotalLuck()         { return baseLuck         + bonusLuck;         }
        public double getTotalFerocity()     { return baseFerocity     + bonusFerocity;     }
    }
}
