package com.hypi;

import com.hypi.commands.IslandCommands;
import com.hypi.rules.WorldRules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HypI implements ModInitializer {

    public static final String MOD_ID = "hypi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("HypI Mod Initializing...");

        // Register world rules (no grief on hub, free build on island)
        WorldRules.register();

        // Register commands (/is, /hub, /spawn)
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            IslandCommands.register(dispatcher);
        });

        LOGGER.info("HypI Mod Loaded!");
    }
}
