package com.hypi;

import com.hypi.commands.IslandCommands;
import com.hypi.economy.BankCommands;
import com.hypi.economy.CoinDropHandler;
import com.hypi.location.LocationCommands;
import com.hypi.location.LocationManager;
import com.hypi.rules.WorldRules;
import com.hypi.scoreboard.HypIScoreboard;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HypI implements ModInitializer {

    public static final String MOD_ID = "hypi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("HypI Mod Initializing...");

        // World rules
        WorldRules.register();

        // Economy
        CoinDropHandler.register();

        // Scoreboard
        HypIScoreboard.register();

        // Commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            IslandCommands.register(dispatcher);
            BankCommands.register(dispatcher);
            LocationCommands.register(dispatcher);
        });

        // Register default Hub locations (overworld)
        registerDefaultLocations();

        LOGGER.info("HypI Loaded!");
    }

    private void registerDefaultLocations() {
        // Hub spawn area
        LocationManager.addLocation(
            "hub_spawn",
            "§aHub",
            "minecraft:overworld",
            new BlockPos(-50, 60, -50),
            new BlockPos(50, 120, 50)
        );

        // Player island world
        LocationManager.addLocation(
            "player_island",
            "§bYour Island",
            "hypi:island_world",
            new BlockPos(-500, 0, -500),
            new BlockPos(500, 384, 500)
        );
    }
}
