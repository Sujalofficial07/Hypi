package com.hypi.scoreboard;

import com.hypi.economy.EconomyData;
import com.hypi.location.LocationManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HypIScoreboard {

    private static final String OBJECTIVE_NAME = "hypi_sidebar";
    private static int tickCounter = 0;
    // Track scoreboard per player
    private static final Map<UUID, ScoreboardObjective> playerObjectives = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            // Update every 10 ticks (0.5 seconds) for performance
            if (tickCounter % 10 != 0) return;

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                updateScoreboard(server, player);
                // Update location every tick
                LocationManager.updatePlayerLocation(player);
            }
        });
    }

    private static void updateScoreboard(MinecraftServer server, ServerPlayerEntity player) {
        Scoreboard scoreboard = player.getScoreboard();

        // Remove old objective
        ScoreboardObjective existing = scoreboard.getNullableObjective(OBJECTIVE_NAME);
        if (existing != null) {
            scoreboard.removeObjective(existing);
        }

        // Create new objective
        ScoreboardObjective objective = scoreboard.addObjective(
            OBJECTIVE_NAME,
            ScoreboardCriterion.DUMMY,
            Text.literal("§6§lSKYBLOCK"),
            ScoreboardCriterion.RenderType.INTEGER,
            true,
            null
        );

        // Set display slot to sidebar
        scoreboard.setObjectiveSlot(ScoreboardDisplaySlot.SIDEBAR, objective);

        // Get current date
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));

        // Get location
        String locationName = LocationManager.getCurrentLocation(player);
        String locationDisplay = locationName.isEmpty() ? "§7Unknown" :
            LocationManager.getDisplayName(locationName);

        // Get economy
        String purse = EconomyData.format(EconomyData.getPurse(player));
        String bank = EconomyData.format(EconomyData.getBank(player));

        // Build scoreboard lines (Hypixel style, bottom to top order)
        // Line numbers go from high (top) to low (bottom)
        setLine(scoreboard, objective, "§r§f ", 14);                          // blank
        setLine(scoreboard, objective, "§e" + date, 13);                      // date
        setLine(scoreboard, objective, "§r§1 ", 12);                          // blank
        setLine(scoreboard, objective, "§7⏣ " + locationDisplay, 11);         // location
        setLine(scoreboard, objective, "§r§2 ", 10);                          // blank
        setLine(scoreboard, objective, "§7Purse: §6" + purse + " Coins", 9);  // purse
        setLine(scoreboard, objective, "§7Bank: §6" + bank + " Coins", 8);    // bank
        setLine(scoreboard, objective, "§r§3 ", 7);                           // blank
        setLine(scoreboard, objective, "§fSkills:", 6);                       // skills header
        setLine(scoreboard, objective, "§7Coming soon...", 5);                // placeholder
        setLine(scoreboard, objective, "§r§4 ", 4);                           // blank
        setLine(scoreboard, objective, "§ewww.hypi.net", 3);                  // server name
        setLine(scoreboard, objective, "§r§5 ", 2);                           // blank
    }

    private static void setLine(Scoreboard scoreboard, ScoreboardObjective objective,
                                 String text, int score) {
        ScoreHolder holder = ScoreHolder.fromName(text);
        scoreboard.getOrCreateScore(holder, objective).setScore(score);
    }
}
