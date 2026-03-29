package com.hypi.menu;

import com.hypi.data.PlayerDataManager;
import com.hypi.level.SkyBlockLevel;
import com.hypi.stats.StatsManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.UUID;

public class MenuCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        // /skyblock — main menu overview
        // /skyblock command — opens chest GUI
    dispatcher.register(CommandManager.literal("skyblock")
       .executes(ctx -> {
           ServerPlayerEntity p = ctx.getSource().getPlayer();
           if (p != null) SkyBlockMenu.open(p);
           return 1;
       })
   );

        // /stats — show all stats
        dispatcher.register(CommandManager.literal("stats")
            .executes(ctx -> {
                ServerPlayerEntity p = ctx.getSource().getPlayer();
                if (p == null) return 0;

                p.sendMessage(Text.literal(
                    "\n§6§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r\n" +
                    "§e§l         YOUR STATS\n" +
                    "§6§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r\n" +
                    StatsManager.formatStats(p) + "\n" +
                    "§6§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r"
                ), false);
                return 1;
            })
        );

        // /profile — show profile info
        dispatcher.register(CommandManager.literal("profile")
            .executes(ctx -> {
                ServerPlayerEntity p = ctx.getSource().getPlayer();
                if (p == null) return 0;
                PlayerDataManager.PlayerData data = PlayerDataManager.get(p);
                int level = SkyBlockLevel.getLevel(p);
                long played = (System.currentTimeMillis() - data.joinTime) / 1000 / 60;

                p.sendMessage(Text.literal(
                    "\n§6§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r\n" +
                    "§e§l         PROFILE\n" +
                    "§6§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r\n" +
                    "§7Player: §f" + p.getName().getString() + "\n" +
                    "§7Profile: §f" + data.profileName + "\n" +
                    "§7SkyBlock Level: §e" + level + "\n" +
                    "§7Time Played: §e" + played + " mins\n" +
                    "§7Purse: §6" + com.hypi.economy.EconomyData.format(
                        com.hypi.economy.EconomyData.getPurse(p)) + " Coins\n" +
                    "§7Bank: §6" + com.hypi.economy.EconomyData.format(
                        com.hypi.economy.EconomyData.getBank(p)) + " Coins\n" +
                    "§6§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r"
                ), false);
                return 1;
            })
        );
    }
}
