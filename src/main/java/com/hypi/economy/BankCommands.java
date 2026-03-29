package com.hypi.economy;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class BankCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        // /bank deposit <amount>
        dispatcher.register(CommandManager.literal("bank")
            .then(CommandManager.literal("deposit")
                .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(1))
                    .executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        double amount = DoubleArgumentType.getDouble(ctx, "amount");
                        if (player == null) return 0;

                        if (!EconomyData.takePurse(player, amount)) {
                            player.sendMessage(Text.literal("§cYou don't have enough coins in your purse!"), false);
                            return 0;
                        }
                        EconomyData.addBank(player, amount);
                        player.sendMessage(Text.literal(
                            "§aDeposited §6" + EconomyData.format(amount) +
                            " Coins §ainto your bank! §7Bank: §6" +
                            EconomyData.format(EconomyData.getBank(player))
                        ), false);
                        return 1;
                    })
                )
                // /bank deposit all
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (player == null) return 0;
                    double purse = EconomyData.getPurse(player);
                    if (purse <= 0) {
                        player.sendMessage(Text.literal("§cYour purse is empty!"), false);
                        return 0;
                    }
                    EconomyData.addBank(player, purse);
                    EconomyData.setPurse(player, 0);
                    player.sendMessage(Text.literal(
                        "§aDeposited all §6" + EconomyData.format(purse) + " Coins §ainto your bank!"
                    ), false);
                    return 1;
                })
            )

            // /bank withdraw <amount>
            .then(CommandManager.literal("withdraw")
                .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(1))
                    .executes(ctx -> {
                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                        double amount = DoubleArgumentType.getDouble(ctx, "amount");
                        if (player == null) return 0;

                        if (!EconomyData.takeBank(player, amount)) {
                            player.sendMessage(Text.literal("§cYou don't have enough coins in your bank!"), false);
                            return 0;
                        }
                        EconomyData.addPurse(player, amount);
                        player.sendMessage(Text.literal(
                            "§aWithdrew §6" + EconomyData.format(amount) +
                            " Coins §afrom your bank! §7Purse: §6" +
                            EconomyData.format(EconomyData.getPurse(player))
                        ), false);
                        return 1;
                    })
                )
            )

            // /bank balance
            .then(CommandManager.literal("balance")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (player == null) return 0;
                    player.sendMessage(Text.literal(
                        "§6Bank Balance: §a" + EconomyData.format(EconomyData.getBank(player)) + " Coins\n" +
                        "§6Purse: §a" + EconomyData.format(EconomyData.getPurse(player)) + " Coins"
                    ), false);
                    return 1;
                })
            )
        );
    }
}
