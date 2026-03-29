package com.hypi.gui;

import com.hypi.data.PlayerDataManager;
import com.hypi.data.PlayerDataManager.PlayerData;
import com.hypi.economy.EconomyData;
import com.hypi.level.SkyBlockLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Items;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SkyBlockMenu {

    public static void open(ServerPlayerEntity player) {
        PlayerData data  = PlayerDataManager.get(player);
        int level        = SkyBlockLevel.getLevel(player);
        String purse     = EconomyData.format(EconomyData.getPurse(player));
        String bank      = EconomyData.format(EconomyData.getBank(player));

        SimpleInventory inv = new SimpleInventory(54);

        // Fill all with gray glass
        for (int i = 0; i < 54; i++) inv.setStack(i, GuiHandler.filler());

        // Row 1
        inv.setStack(0,  GuiHandler.makeGlowItem(Items.NETHER_STAR,
            "§e§lSkyBlock Level §8» §e" + level,
            "§7", "§7Total XP: §e" + String.format("%.0f", data.skyBlockXP),
            "§7", SkyBlockLevel.getProgressBar(player),
            "§7", "§eClick to view level!"));

        inv.setStack(1,  GuiHandler.makeItem(Items.DIAMOND_SWORD,
            "§c§lYour Stats", "§7",
            "§c❤ Health: §f"      + fmt(data.getTotalHealth()),
            "§a❈ Defence: §f"     + fmt(data.getTotalDefence()),
            "§f✦ Speed: §f"       + fmt(data.getTotalSpeed()),
            "§c⚔ Strength: §f"    + fmt(data.getTotalStrength()),
            "§9☣ Crit Chance: §f" + fmt(data.getTotalCritChance()) + "%",
            "§7", "§eClick to view all stats!"));

        inv.setStack(2,  GuiHandler.makeItem(Items.ENCHANTING_TABLE,
            "§a§lSkills", "§7",
            "§7⚔ Combat  §7⛏ Mining  §7❀ Farming",
            "§7🎣 Fishing  §7✿ Foraging",
            "§7", "§eClick to view Skills!"));

        inv.setStack(3,  GuiHandler.makeItem(Items.BOOK,
            "§6§lCollections", "§7",
            "§7Track all gathered items.", "§7",
            "§eClick to view Collections!"));

        inv.setStack(4,  GuiHandler.makeItem(Items.CRAFTING_TABLE,
            "§e§lMinions", "§7",
            "§7Auto-gather resources on your island.", "§7",
            "§eClick to view Minions!"));

        inv.setStack(5,  GuiHandler.makeItem(Items.CHEST,
            "§6§lBags", "§7",
            "§7Store items in special bags.", "§7",
            "§eClick to view Bags!"));

        inv.setStack(6,  GuiHandler.makeItem(Items.BONE,
            "§a§lPets", "§7",
            "§7View and manage your Pets.", "§7",
            "§eClick to view Pets!"));

        inv.setStack(7,  GuiHandler.makeItem(Items.LEATHER_CHESTPLATE,
            "§5§lAccessory Bag", "§7",
            "§7Manage your Accessories.", "§7",
            "§eClick to view Accessories!"));

        inv.setStack(8,  GuiHandler.makeItem(Items.PLAYER_HEAD,
            "§e§lYour SkyBlock Profile", "§7",
            "§7Player: §f" + player.getName().getString(),
            "§7Profile: §f" + data.profileName,
            "§7Level: §e" + level, "§7",
            "§eClick to view Profile!"));

        // Row 2
        inv.setStack(9,  GuiHandler.makeItem(Items.MAP,
            "§e§lQuests", "§7", "§7Complete quests to earn rewards.", "§7",
            "§eClick to view Quests!"));

        inv.setStack(10, GuiHandler.makeItem(Items.SKELETON_SKULL,
            "§f§lBestiary", "§7", "§7Track all mobs you've killed.", "§7",
            "§eClick to view Bestiary!"));

        inv.setStack(11, GuiHandler.makeItem(Items.BLAZE_POWDER,
            "§c§lSlayer Quests", "§7",
            "§7Slay powerful monsters for rewards.", "§7",
            "§eClick to view Slayers!"));

        inv.setStack(12, GuiHandler.makeItem(Items.WITHER_SKELETON_SKULL,
            "§8§lThe Catacombs", "§7",
            "§7Enter the Dungeons.", "§7",
            "§eClick to view Catacombs!"));

        inv.setStack(13, GuiHandler.makeItem(Items.FIREWORK_ROCKET,
            "§b§lEvents", "§7",
            "§7View current and upcoming events.", "§7",
            "§eClick to view Events!"));

        inv.setStack(14, GuiHandler.makeItem(Items.PINK_DYE,
            "§d§lFairy Souls", "§7",
            "§7Found: §d" + data.fairySouls + "§7/§d227", "§7",
            "§eClick to view Fairy Souls!"));

        inv.setStack(15, GuiHandler.makeItem(Items.COOKIE,
            "§6§lBooster Cookie", "§7",
            "§7Boost your SkyBlock experience.", "§7",
            "§eClick to view Booster Cookie!"));

        inv.setStack(16, GuiHandler.makeItem(Items.ENDER_PEARL,
            "§5§lTravel Scrolls", "§7",
            "§7Teleport to unlocked locations.", "§7",
            "§eClick to view Travel Scrolls!"));

        inv.setStack(17, GuiHandler.makeItem(Items.REDSTONE,
            "§c§lSkyBlock Settings", "§7",
            "§7Customize your SkyBlock experience.", "§7",
            "§eClick to view Settings!"));

        // Row 3
        inv.setStack(18, GuiHandler.makeGlowItem(Items.GOLD_NUGGET,
            "§6§lCoins", "§7",
            "§7Purse: §6" + purse + " Coins",
            "§7Bank:  §6" + bank  + " Coins", "§7",
            "§e/bank deposit §7or §e/bank withdraw"));

        inv.setStack(19, GuiHandler.makeItem(Items.GOLD_BLOCK,
            "§6§lBanking", "§7",
            "§7Purse: §6" + purse + " Coins",
            "§7Bank:  §6" + bank  + " Coins", "§7",
            "§e/bank deposit <amount>",
            "§e/bank withdraw <amount>",
            "§e/bank balance"));

        inv.setStack(20, GuiHandler.makeItem(Items.ENDER_CHEST,
            "§b§lStorage", "§7",
            "§7Access your personal storage.", "§7",
            "§eClick to open Storage!"));

        inv.setStack(21, GuiHandler.makeItem(Items.BUNDLE,
            "§6§lSacks", "§7",
            "§7Store large amounts of items.", "§7",
            "§eClick to view Sacks!"));

        inv.setStack(22, GuiHandler.makeGlowItem(Items.CRAFTING_TABLE,
            "§a§lCrafting", "§7",
            "§7Open your crafting menu.", "§7",
            "§eClick to open Crafting!"));

        inv.setStack(23, GuiHandler.makeItem(Items.VILLAGER_SPAWN_EGG,
            "§e§lNPCs", "§7",
            "§7View and interact with NPCs.", "§7",
            "§eClick to view NPCs!"));

        inv.setStack(24, GuiHandler.makeItem(Items.IRON_CHESTPLATE,
            "§7§lArmor & Equipment", "§7",
            "§7View equipped Armor and Equipment.", "§7",
            "§eClick to view Armor!"));

        inv.setStack(25, GuiHandler.makeItem(Items.EMERALD,
            "§a§lPower Stones", "§7",
            "§7Slot Power Stones to boost stats.", "§7",
            "§eClick to view Power Stones!"));

        inv.setStack(26, GuiHandler.makeItem(Items.WRITTEN_BOOK,
            "§f§lFurther Reading", "§7",
            "§7Learn more about SkyBlock.", "§7",
            "§eClick to read more!"));

        // Row 4
        inv.setStack(27, GuiHandler.makeItem(Items.BREWING_STAND,
            "§5§lPotions", "§7",
            "§7View active potions and effects.", "§7",
            "§eClick to view Potions!"));

        inv.setStack(28, GuiHandler.makeItem(Items.ANVIL,
            "§7§lItem Modifiers", "§7",
            "§7Reforge and modify your items.", "§7",
            "§eClick to view Item Modifiers!"));

        inv.setStack(29, GuiHandler.makeItem(Items.CLOCK,
            "§e§lSkyBlock Time", "§7",
            "§7SkyBlock Year 1, Early Summer", "§7",
            "§eClick to view Calendar!"));

        inv.setStack(30, GuiHandler.makeItem(Items.COMPASS,
            "§7§lHistory", "§7",
            "§7View your SkyBlock history.", "§7",
            "§eClick to view History!"));

        inv.setStack(31, GuiHandler.makeItem(Items.IRON_SWORD,
            "§c§lCombat", "§7",
            "§7View combat stats and kill counts.", "§7",
            "§eClick to view Combat!"));

        inv.setStack(32, GuiHandler.makeItem(Items.COMPARATOR,
            "§b§lStats Tuning", "§7",
            "§7Fine-tune your stat allocations.", "§7",
            "§eClick to view Stats Tuning!"));

        inv.setStack(33, GuiHandler.makeItem(Items.DRAGON_HEAD,
            "§5§lBoss Collections", "§7",
            "§7Track Boss kill records.", "§7",
            "§eClick to view Boss Collections!"));

        inv.setStack(34, GuiHandler.makeItem(Items.FILLED_MAP,
            "§a§lLocations", "§7",
            "§7View all SkyBlock locations.", "§7",
            "§eClick to view Locations!"));

        inv.setStack(35, GuiHandler.makeItem(Items.EMERALD,
            "§a§lTrading", "§7",
            "§7Trade items with the Bazaar.", "§7",
            "§eClick to view Trading!"));

        // Row 5+6 fillers + close button
        inv.setStack(49, GuiHandler.makeItem(Items.BARRIER,
            "§c§lClose", "§7", "§7Close this menu."));

        // Open screen with click handler
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> new GuiScreenHandler(syncId, playerInv, inv) {
                @Override
                protected void handleClick(int slot, PlayerEntity clicker) {
                    if (!(clicker instanceof ServerPlayerEntity sp)) return;
                    sp.closeHandledScreen();
                    switch (slot) {
                        case 49 -> {} // close
                        default -> sp.sendMessage(
                            Text.literal("§eComing soon!"), false);
                    }
                }
            },
            Text.literal("§0SkyBlock Menu")
        ));
    }

    private static String fmt(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.format("%.1f", v);
    }
}
