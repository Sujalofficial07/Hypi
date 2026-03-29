package com.hypi.menu;

import com.hypi.gui.SkyBlockMenu;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class MenuCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("skyblock")
            .executes(ctx -> {
                ServerPlayerEntity p = ctx.getSource().getPlayer();
                if (p != null) SkyBlockMenu.open(p);
                return 1;
            })
        );
    }
}
