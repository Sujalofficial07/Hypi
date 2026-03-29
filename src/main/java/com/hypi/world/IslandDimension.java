package com.hypi.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class IslandDimension {

    public static final RegistryKey<World> ISLAND_WORLD_KEY = RegistryKey.of(
        RegistryKeys.WORLD,
        new Identifier("hypi", "island_world")
    );
}
