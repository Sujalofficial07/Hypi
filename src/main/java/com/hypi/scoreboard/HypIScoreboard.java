package com.hypi.scoreboard;

import com.hypi.economy.EconomyData;
import com.hypi.level.SkyBlockLevel;
import com.hypi.location.LocationManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HypIScoreboard {

    private static final String OBJ = "hypi_sb";
    private static int tick = 0;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tick++;
            if (tick % 10 != 0) return;
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                updateScoreboard(server, player);
                LocationManager.updatePlayerLocation(player);
            }
        });
    }

    private static void updateScoreboard(MinecraftServer server, ServerPlayerEntity player) {
        Scoreboard sb = server.getScoreboard();

        // Remove old objective
        ScoreboardObjective old = sb.getNullableObjective(OBJ);
        if (old != null) sb.removeObjective(old);

        // Create new objective — 4 arg version for 1.20.1
        ScoreboardObjective obj = sb.addObjective(
            OBJ,
            ScoreboardCriterion.DUMMY,
            Text.literal("§6§lSKYBLOCK"),
            ScoreboardCriterion.RenderType.INTEGER
        );

        // Set sidebar display slot
        sb.setObjectiveSlot(ScoreboardDisplaySlot.VALUES[1], obj);

        // Build lines
        String date     = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
        String locName  = LocationManager.getCurrentLocation(player);
        String locDisp  = locName.isEmpty() ? "§7Unknown"
                        : LocationManager.getDisplayName(locName);
        String purse    = EconomyData.format(EconomyData.getPurse(player));
        String bank     = EconomyData.format(EconomyData.getBank(player));
        int    level    = SkyBlockLevel.getLevel(player);
        String progress = SkyBlockLevel.getProgressBar(player);

        setLine(sb, obj, "§r§f ",        14);
        setLine(sb, obj, "§e" + date,    13);
        setLine(sb, obj, "§r§1 ",        12);
        setLine(sb, obj, "§7⏣ " + locDisp, 11);
        setLine(sb, obj, "§r§2 ",        10);
        setLine(sb, obj, "§7Purse: §6" + purse + " Coins", 9);
        setLine(sb, obj, "§7Bank: §6"  + bank  + " Coins", 8);
        setLine(sb, obj, "§r§3 ",         7);
        setLine(sb, obj, "§fLevel: §e" + level, 6);
        setLine(sb, obj, progress,         5);
        setLine(sb, obj, "§r§4 ",         4);
        setLine(sb, obj, "§ewww.hypi.net", 3);
        setLine(sb, obj, "§r§5 ",         2);
    }

    private static void setLine(Scoreboard sb, ScoreboardObjective obj, String text, int score) {
        // 1.20.1 uses Team fake players for scoreboard lines
        String teamName = "hypi_line_" + score;

        // Create team if not exists
        Team team = sb.getTeam(teamName);
        if (team == null) team = sb.addTeam(teamName);
        team.setPrefix(Text.literal(text));

        // Add fake player to team
        String fakePlayer = "§" + (char)('a' + score);
        sb.addScoreHolderToTeam(fakePlayer, team);

        // Set score
        ScoreboardPlayerScore s = sb.getPlayerScore(fakePlayer, obj);
        s.setScore(score);
    }
}
