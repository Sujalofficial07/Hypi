package com.hypi.gui;

import com.hypi.data.PlayerDataManager;
import com.hypi.economy.EconomyData;
import com.hypi.level.SkyBlockLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SkyBlockMenu {

    public static void open(ServerPlayerEntity player) {
        SimpleInventory inv = new SimpleInventory(54);
        PlayerDataManager.PlayerData data = PlayerDataManager.get(player);
        int level = SkyBlockLevel.getLevel(player);
        String purse = EconomyData.format(EconomyData.getPurse(player));
        String bank  = EconomyData.format(EconomyData.getBank(player));

        // === BORDER FILLERS ===
        for (int i = 0; i < 54; i++) inv.setStack(i, GuiHandler.filler());

        // === ROW 1 — Top items ===

        // Slot 0 — SkyBlock Level (Nether Star)
        inv.setStack(0, GuiHandler.makeGlowItem(Items.NETHER_STAR,
            "§e§lSkyBlock Level §8- §e" + level,
            "§7Your SkyBlock progression level.",
            "§7",
            "§7Total XP: §e" + String.format("%.0f", data.skyBlockXP),
            "§7",
            SkyBlockLevel.getProgressBar(player),
            "§7",
            "§eClick to view level rewards!"
        ));

        // Slot 1 — Stats (Diamond Sword)
        inv.setStack(1, GuiHandler.makeItem(Items.DIAMOND_SWORD,
            "§c§lYour Stats",
            "§7",
            "§c❤ Health: §f" + fmt(data.maxHealth),
            "§a❈ Defence: §f" + fmt(data.defence),
            "§f✦ Speed: §f" + fmt(data.speed),
            "§c⚔ Strength: §f" + fmt(data.strength),
            "§9☣ Crit Chance: §f" + fmt(data.critChance) + "%",
            "§9☠ Crit Damage: §f" + fmt(data.critDamage) + "%",
            "§b✎ Intelligence: §f" + fmt(data.intelligence),
            "§7",
            "§eClick to view all stats!"
        ));

        // Slot 2 — Skills (Enchanting Table)
        inv.setStack(2, GuiHandler.makeItem(Items.ENCHANTING_TABLE,
            "§a§lSkills",
            "§7",
            "§7View and manage your skills.",
            "§7",
            "§7⚔ Combat: §eComing soon",
            "§7⛏ Mining: §eComing soon",
            "§7❀ Farming: §eComing soon",
            "§7",
            "§eClick to view Skills!"
        ));

        // Slot 3 — Collections (Book)
        inv.setStack(3, GuiHandler.makeItem(Items.BOOK,
            "§6§lCollections",
            "§7",
            "§7Track your item collections.",
            "§7",
            "§eClick to view Collections!"
        ));

        // Slot 4 — Minions (Crafting Table)
        inv.setStack(4, GuiHandler.makeItem(Items.CRAFTING_TABLE,
            "§e§lMinions",
            "§7",
            "§7Place Minions on your island to",
            "§7automatically gather resources.",
            "§7",
            "§eClick to view Minions!"
        ));

        // Slot 5 — Bags (Chest)
        inv.setStack(5, GuiHandler.makeItem(Items.CHEST,
            "§6§lBags",
            "§7",
            "§7Store items in special bags.",
            "§7",
            "§eClick to view Bags!"
        ));

        // Slot 6 — Pets (Bone)
        inv.setStack(6, GuiHandler.makeItem(Items.BONE,
            "§a§lPets",
            "§7",
            "§7View and manage your Pets.",
            "§7",
            "§eClick to view Pets!"
        ));

        // Slot 7 — Accessories (Leather Chestplate)
        inv.setStack(7, GuiHandler.makeItem(Items.LEATHER_CHESTPLATE,
            "§5§lAccessory Bag",
            "§7",
            "§7Manage your Accessories.",
            "§7",
            "§eClick to view Accessories!"
        ));

        // Slot 8 — Profiles (Player Head)
        inv.setStack(8, GuiHandler.makeItem(Items.PLAYER_HEAD,
            "§e§lYour SkyBlock Profile",
            "§7",
            "§7Player: §f" + player.getName().getString(),
            "§7Profile: §f" + data.profileName,
            "§7Level: §e" + level,
            "§7",
            "§eClick to view Profile!"
        ));

        // === ROW 2 ===

        // Slot 9 — Quests (Map)
        inv.setStack(9, GuiHandler.makeItem(Items.MAP,
            "§e§lQuests",
            "§7",
            "§7Complete Quests to earn",
            "§7rewards and XP.",
            "§7",
            "§eClick to view Quests!"
        ));

        // Slot 10 — Bestiary (Skeleton Skull)
        inv.setStack(10, GuiHandler.makeItem(Items.SKELETON_SKULL,
            "§f§lBestiary",
            "§7",
            "§7Track all the mobs you've killed.",
            "§7",
            "§eClick to view Bestiary!"
        ));

        // Slot 11 — Slayers (Blaze Powder)
        inv.setStack(11, GuiHandler.makeItem(Items.BLAZE_POWDER,
            "§c§lSlayer Quests",
            "§7",
            "§7Slay powerful monsters to earn",
            "§7Slayer XP and rewards.",
            "§7",
            "§eClick to view Slayers!"
        ));

        // Slot 12 — The Catacombs (Wither Skeleton Skull)
        inv.setStack(12, GuiHandler.makeItem(Items.WITHER_SKELETON_SKULL,
            "§8§lThe Catacombs",
            "§7",
            "§7Enter the Dungeons and fight",
            "§7your way through The Catacombs.",
            "§7",
            "§eClick to view Catacombs!"
        ));

        // Slot 13 — Events (Firework)
        inv.setStack(13, GuiHandler.makeItem(Items.FIREWORK_ROCKET,
            "§b§lEvents",
            "§7",
            "§7View current and upcoming events.",
            "§7",
            "§eClick to view Events!"
        ));

        // Slot 14 — Fairy Souls (Pink Dye)
        inv.setStack(14, GuiHandler.makeItem(Items.PINK_DYE,
            "§d§lFairy Souls",
            "§7",
            "§7Collect Fairy Souls to boost",
            "§7your stats permanently.",
            "§7",
            "§7Found: §d0§7/§d227",
            "§7",
            "§eClick to view Fairy Souls!"
        ));

        // Slot 15 — Booster Cookie (Cookie)
        inv.setStack(15, GuiHandler.makeItem(Items.COOKIE,
            "§6§lBooster Cookie",
            "§7",
            "§7Boost your SkyBlock experience",
            "§7with a Booster Cookie.",
            "§7",
            "§eClick to view Booster Cookie!"
        ));

        // Slot 16 — Travel Scrolls (Ender Pearl)
        inv.setStack(16, GuiHandler.makeItem(Items.ENDER_PEARL,
            "§5§lTravel Scrolls",
            "§7",
            "§7Teleport to unlocked locations",
            "§7using Travel Scrolls.",
            "§7",
            "§eClick to view Travel Scrolls!"
        ));

        // Slot 17 — Settings (Redstone)
        inv.setStack(17, GuiHandler.makeItem(Items.REDSTONE,
            "§c§lSkyBlock Settings",
            "§7",
            "§7Customize your SkyBlock",
            "§7experience.",
            "§7",
            "§eClick to view Settings!"
        ));

        // === ROW 3 ===

        // Slot 18 — Coins display (Gold Nugget)
        inv.setStack(18, GuiHandler.makeGlowItem(Items.GOLD_NUGGET,
            "§6§lCoins",
            "§7",
            "§7Purse: §6" + purse + " Coins",
            "§7Bank: §6" + bank + " Coins",
            "§7",
            "§eUse §6/bank §eto deposit or withdraw!"
        ));

        // Slot 19 — Banking (Gold Block)
        inv.setStack(19, GuiHandler.makeItem(Items.GOLD_BLOCK,
            "§6§lBanking",
            "§7",
            "§7Bank Balance: §6" + bank + " Coins",
            "§7Purse: §6" + purse + " Coins",
            "§7",
            "§7§o\"The Bank never loses!\"",
            "§7",
            "§e/bank deposit <amount>",
            "§e/bank withdraw <amount>",
            "§e/bank balance"
        ));

        // Slot 20 — Storage (Ender Chest)
        inv.setStack(20, GuiHandler.makeItem(Items.ENDER_CHEST,
            "§b§lStorage",
            "§7",
            "§7Access your personal storage.",
            "§7",
            "§eClick to open Storage!"
        ));

        // Slot 21 — Sacks (Bundle)
        inv.setStack(21, GuiHandler.makeItem(Items.BUNDLE,
            "§6§lSacks of Gold",
            "§7",
            "§7Store large amounts of items",
            "§7in special Sacks.",
            "§7",
            "§eClick to view Sacks!"
        ));

        // Slot 22 — Crafting (Crafting Table) center
        inv.setStack(22, GuiHandler.makeGlowItem(Items.CRAFTING_TABLE,
            "§a§lCrafting",
            "§7",
            "§7Open your crafting menu.",
            "§7",
            "§eClick to open Crafting!"
        ));

        // Slot 23 — NPCs (Villager Spawn Egg)
        inv.setStack(23, GuiHandler.makeItem(Items.VILLAGER_SPAWN_EGG,
            "§e§lNPCs",
            "§7",
            "§7View and interact with NPCs.",
            "§7",
            "§eClick to view NPCs!"
        ));

        // Slot 24 — Armor (Iron Chestplate)
        inv.setStack(24, GuiHandler.makeItem(Items.IRON_CHESTPLATE,
            "§7§lArmor & Equipment",
            "§7",
            "§7View your equipped Armor",
            "§7and Equipment stats.",
            "§7",
            "§eClick to view Armor!"
        ));

        // Slot 25 — Power Stones (Emerald)
        inv.setStack(25, GuiHandler.makeItem(Items.EMERALD,
            "§a§lPower Stones",
            "§7",
            "§7Slot Power Stones to boost",
            "§7your stats.",
            "§7",
            "§eClick to view Power Stones!"
        ));

        // Slot 26 — Further Reading (Written Book)
        inv.setStack(26, GuiHandler.makeItem(Items.WRITTEN_BOOK,
            "§f§lFurther Reading",
            "§7",
            "§7Learn more about SkyBlock",
            "§7features and mechanics.",
            "§7",
            "§eClick to read more!"
        ));

        // === ROW 4 — Potions row ===
        inv.setStack(27, GuiHandler.makeItem(Items.BREWING_STAND,
            "§5§lPotions",
            "§7",
            "§7View your active potions",
            "§7and effects.",
            "§7",
            "§eClick to view Potions!"
        ));

        // Slot 28 — Item Modifiers (Anvil)
        inv.setStack(28, GuiHandler.makeItem(Items.ANVIL,
            "§7§lItem Modifiers",
            "§7",
            "§7Reforge and modify your items.",
            "§7",
            "§eClick to view Item Modifiers!"
        ));

        // Slot 29 — SkyBlock Time (Clock)
        inv.setStack(29, GuiHandler.makeItem(Items.CLOCK,
            "§e§lSkyBlock Time",
            "§7",
            "§7View the current SkyBlock time",
            "§7and season.",
            "§7",
            "§7SkyBlock Year 1, Early Summer",
            "§7",
            "§eClick to view SkyBlock Calendar!"
        ));

        // Slot 30 — History (Compass)
        inv.setStack(30, GuiHandler.makeItem(Items.COMPASS,
            "§7§lHistory",
            "§7",
            "§7View your SkyBlock history",
            "§7and past activities.",
            "§7",
            "§eClick to view History!"
        ));

        // Slot 31 — Combat (Sword)
        inv.setStack(31, GuiHandler.makeItem(Items.DIAMOND_SWORD,
            "§c§lCombat",
            "§7",
            "§7View your combat stats",
            "§7and kill counts.",
            "§7",
            "§eClick to view Combat!"
        ));

        // Slot 32 — Stats Tuning (Comparator)
        inv.setStack(32, GuiHandler.makeItem(Items.COMPARATOR,
            "§b§lStats Tuning",
            "§7",
            "§7Fine-tune your stat allocations.",
            "§7",
            "§eClick to view Stats Tuning!"
        ));

        // Slot 33 — Boss Collections (Dragon Head)
        inv.setStack(33, GuiHandler.makeItem(Items.DRAGON_HEAD,
            "§5§lBoss Collections",
            "§7",
            "§7Track your Boss kill records",
            "§7and rewards.",
            "§7",
            "§eClick to view Boss Collections!"
        ));

        // Slot 34 — Locations (Map)
        inv.setStack(34, GuiHandler.makeItem(Items.FILLED_MAP,
            "§a§lLocations",
            "§7",
            "§7View all SkyBlock locations.",
            "§7",
            "§eClick to view Locations!"
        ));

        // Slot 35 — Trading (Emerald)
        inv.setStack(35, GuiHandler.makeItem(Items.EMERALD,
            "§a§lTrading",
            "§7",
            "§7Trade items with other players",
            "§7or the Bazaar.",
            "§7",
            "§eClick to view Trading!"
        ));

        // === ROW 5 + 6 — Bottom fillers with close button ===
        for (int i = 36; i < 54; i++) inv.setStack(i, GuiHandler.filler());

        // Slot 49 — Close button (Barrier)
        inv.setStack(49, GuiHandler.makeItem(Items.BARRIER,
            "§c§lClose",
            "§7",
            "§7Close this menu."
        ));

        // Open the menu with click handler
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, playerInv, p) -> new GuiScreenHandler(syncId, playerInv, inv) {
                @Override
                protected void handleClick(int slot, PlayerEntity clicker) {
                    if (!(clicker instanceof ServerPlayerEntity sp)) return;
                    sp.closeHandledScreen();

                    switch (slot) {
                        case 0  -> sp.sendMessage(Text.literal("§eSkyBlock Level coming soon!"), false);
                        case 1  -> sp.sendMessage(Text.literal("§cStats menu coming soon!"), false);
                        case 2  -> sp.sendMessage(Text.literal("§aSkills menu coming soon!"), false);
                        case 3  -> sp.sendMessage(Text.literal("§6Collections coming soon!"), false);
                        case 4  -> sp.sendMessage(Text.literal("§eMinions coming soon!"), false);
                        case 6  -> sp.sendMessage(Text.literal("§aPets coming soon!"), false);
                        case 7  -> sp.sendMessage(Text.literal("§5Accessory Bag coming soon!"), false);
                        case 9  -> sp.sendMessage(Text.literal("§eQuests coming soon!"), false);
                        case 10 -> sp.sendMessage(Text.literal("§fBestiary coming soon!"), false);
                        case 11 -> sp.sendMessage(Text.literal("§cSlayers coming soon!"), false);
                        case 12 -> sp.sendMessage(Text.literal("§8Catacombs coming soon!"), false);
                        case 13 -> sp.sendMessage(Text.literal("§bEvents coming soon!"), false);
                        case 14 -> sp.sendMessage(Text.literal("§dFairy Souls coming soon!"), false);
                        case 19 -> BankMenuOpen(sp);
                        case 49 -> {} // close — already closed above
                        default -> {}
                    }
                }
            },
            Text.literal("§0SkyBlock Menu")
        ));
    }

    private static void BankMenuOpen(ServerPlayerEntity player) {
        player.sendMessage(Text.literal(
            "§6§lBANK\n" +
            "§7Purse: §6" + EconomyData.format(EconomyData.getPurse(player)) + " Coins\n" +
            "§7Bank: §6" + EconomyData.format(EconomyData.getBank(player)) + " Coins\n" +
            "§7Use: §e/bank deposit <amount> §7or §e/bank withdraw <amount>"
        ), false);
    }

    private static String fmt(double val) {
        return val == (long) val ? String.valueOf((long) val) : String.format("%.1f", val);
    }
                    }
