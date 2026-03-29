package com.hypi.location;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class LocationManager {

    // Stores locations: name -> LocationData
    private static final Map<String, LocationData> locations = new LinkedHashMap<>();
    // Tracks current location per player
    private static final Map<UUID, String> playerLocations = new HashMap<>();

    public static class LocationData {
        public String name;
        public String displayName; // with color codes
        public String dimension;
        public BlockPos pos1;
        public BlockPos pos2;

        public LocationData(String name, String displayName, String dimension, BlockPos pos1, BlockPos pos2) {
            this.name = name;
            this.displayName = displayName;
            this.dimension = dimension;
            this.pos1 = pos1;
            this.pos2 = pos2;
        }

        public boolean contains(BlockPos pos) {
            int minX = Math.min(pos1.getX(), pos2.getX());
            int maxX = Math.max(pos1.getX(), pos2.getX());
            int minY = Math.min(pos1.getY(), pos2.getY());
            int maxY = Math.max(pos1.getY(), pos2.getY());
            int minZ = Math.min(pos1.getZ(), pos2.getZ());
            int maxZ = Math.max(pos1.getZ(), pos2.getZ());

            return pos.getX() >= minX && pos.getX() <= maxX &&
                   pos.getY() >= minY && pos.getY() <= maxY &&
                   pos.getZ() >= minZ && pos.getZ() <= maxZ;
        }
    }

    public static void addLocation(String name, String displayName, String dimension, BlockPos pos1, BlockPos pos2) {
        locations.put(name, new LocationData(name, displayName, dimension, pos1, pos2));
    }

    public static void removeLocation(String name) {
        locations.remove(name);
    }

    public static Map<String, LocationData> getLocations() {
        return locations;
    }

    public static String getCurrentLocation(ServerPlayerEntity player) {
        return playerLocations.getOrDefault(player.getUuid(), "");
    }

    // Call this every tick to update player location
    public static void updatePlayerLocation(ServerPlayerEntity player) {
        BlockPos pos = player.getBlockPos();
        String dim = player.getWorld().getRegistryKey().getValue().toString();

        for (LocationData loc : locations.values()) {
            if (loc.dimension.equals(dim) && loc.contains(pos)) {
                String prev = playerLocations.get(player.getUuid());
                if (!loc.name.equals(prev)) {
                    playerLocations.put(player.getUuid(), loc.name);
                    // Send location change title like Hypixel
                    player.sendMessage(
                        net.minecraft.text.Text.literal("§e" + loc.displayName),
                        true
                    );
                }
                return;
            }
        }
        playerLocations.put(player.getUuid(), "");
    }

    public static String getDisplayName(String locationName) {
        LocationData loc = locations.get(locationName);
        return loc != null ? loc.displayName : "";
    }
}
