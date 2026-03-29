package com.hypi.location;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LocationCommands {

    private static final Map<UUID, BlockPos> pos1 = new HashMap<>();
    private static final Map<UUID, BlockPos> pos2 = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(CommandManager.literal("pos1")
            .requires(s -> s.hasPermissionLevel(2))
            .executes(ctx -> {
                ServerPlayerEntity p = ctx.getSource().getPlayer();
                if (p == null) return 0;
                pos1.put(p.getUuid(), p.getBlockPos());
                p.sendMessage(Text.literal("§aPos1 set to §f" + p.getBlockPos().toShortString()), false);
                return 1;
            }));

        dispatcher.register(CommandManager.literal("pos2")
            .requires(s -> s.hasPermissionLevel(2))
            .executes(ctx -> {
                ServerPlayerEntity p = ctx.getSource().getPlayer();
                if (p == null) return 0;
                pos2.put(p.getUuid(), p.getBlockPos());
                p.sendMessage(Text.literal("§aPos2 set to §f" + p.getBlockPos().toShortString()), false);
                return 1;
            }));

        dispatcher.register(CommandManager.literal("setlocation")
            .requires(s -> s.hasPermissionLevel(2))
            .then(CommandManager.argument("name", StringArgumentType.word())
                .then(CommandManager.argument("displayname", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        ServerPlayerEntity p = ctx.getSource().getPlayer();
                        if (p == null) return 0;
                        BlockPos p1 = pos1.get(p.getUuid());
                        BlockPos p2 = pos2.get(p.getUuid());
                        if (p1 == null || p2 == null) {
                            p.sendMessage(Text.literal("§cSet /pos1 and /pos2 first!"), false);
                            return 0;
                        }
                        String name = StringArgumentType.getString(ctx, "name");
                        String display = StringArgumentType.getString(ctx, "displayname");
                        String dim = p.getWorld().getRegistryKey().getValue().toString();
                        LocationManager.addLocation(name, display, dim, p1, p2);
                        p.sendMessage(Text.literal("§aLocation §f" + name + " §acreated!"), false);
                        return 1;
                    }))));

        dispatcher.register(CommandManager.literal("removelocation")
            .requires(s -> s.hasPermissionLevel(2))
            .then(CommandManager.argument("name", StringArgumentType.word())
                .executes(ctx -> {
                    String name = StringArgumentType.getString(ctx, "name");
                    LocationManager.removeLocation(name);
                    ctx.getSource().sendFeedback(
                        () -> Text.literal("§aLocation §f" + name + " §aremoved!"), false);
                    return 1;
                })));

        dispatcher.register(CommandManager.literal("locations")
            .requires(s -> s.hasPermissionLevel(2))
            .executes(ctx -> {
                if (LocationManager.getLocations().isEmpty()) {
                    ctx.getSource().sendFeedback(() -> Text.literal("§cNo locations!"), false);
                    return 0;
                }
                StringBuilder sb = new StringBuilder("§6--- Locations ---\n");
                LocationManager.getLocations().forEach((n, l) ->
                    sb.append("§f").append(n).append(" §7- ").append(l.displayName).append("\n"));
                ctx.getSource().sendFeedback(() -> Text.literal(sb.toString()), false);
                return 1;
            }));
    }
}
