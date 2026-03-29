package com.hypi.scoreboard;

import com.hypi.economy.EconomyData;
import com.hypi.level.SkyBlockLevel;
import com.hypi.location.LocationManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
        ServerScoreboard sb = server.getScoreboard();

        // Remove old objective
        ScoreboardObjective old = sb.getNullableObjective(OBJ);
        if (old != null) sb.removeObjective(old);

        // Create objective — exact 4-arg signature for 1.20.1
        ScoreboardObjective obj = sb.addObjective(
            OBJ,
            ScoreboardCriterion.DUMMY,
            Text.literal("§6§lSKYBLOCK"),
            ScoreboardCriterion.RenderType.INTEGER
        );

        // Sidebar slot = 1 in 1.20.1
        sb.setObjectiveSlot(1, obj);

        // Data
        String date    = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
        String locName = LocationManager.getCurrentLocation(player);
        String locDisp = locName.isEmpty() ? "§7Unknown"
                       : LocationManager.getDisplayName(locName);
        String purse   = EconomyData.format(EconomyData.getPurse(player));
        String bank    = EconomyData.format(EconomyData.getBank(player));
        int    level   = SkyBlockLevel.getLevel(player);
        String bar     = SkyBlockLevel.getProgressBar(player);

        // Lines (high score = top of sidebar)
        setLine(sb, obj, "§r§f ",                     14);
        setLine(sb, obj, "§e" + date,                 13);
        setLine(sb, obj, "§r§1 ",                     12);
        setLine(sb, obj, "§7⏣ " + locDisp,            11);
        setLine(sb, obj, "§r§2 ",                     10);
        setLine(sb, obj, "§7Purse: §6" + purse + " Coins", 9);
        setLine(sb, obj, "§7Bank: §6"  + bank  + " Coins", 8);
        setLine(sb, obj, "§r§3 ",                      7);
        setLine(sb, obj, "§fLevel: §e" + level,         6);
        setLine(sb, obj, bar,                           5);
        setLine(sb, obj, "§r§4 ",                      4);
        setLine(sb, obj, "§ewww.hypi.net",              3);
        setLine(sb, obj, "§r§5 ",                      2);
    }

    private static void setLine(ServerScoreboard sb, ScoreboardObjective obj,
                                String text, int score) {
        String teamName   = "hypi_l_" + score;
        String fakePlayer = "§" + score;

        // Get or create team
        Team team = sb.getTeam(teamName);
        if (team == null) {
            team = sb.addTeam(teamName);
        }
        team.setPrefix(Text.literal(text));

        // Add fake player to team — correct method name in 1.20.1
        sb.addPlayerToTeam(fakePlayer, team);

        // Set score
        ScoreboardPlayerScore s = sb.getPlayerScore(fakePlayer, obj);
        s.setScore(score);
    }
}
