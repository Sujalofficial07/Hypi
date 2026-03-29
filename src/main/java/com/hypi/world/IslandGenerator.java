package com.hypi.world;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class IslandGenerator {

    // Classic Hypixel Skyblock starter island
    public static void generateStarterIsland(ServerWorld world, BlockPos center) {

        // === DIRT BASE (oval shape like Hypixel) ===
        int[][] dirtLayout = {
            {-3, 0}, {-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {2, 0}, {3, 0},
            {-3, 1}, {-2, 1}, {-1, 1}, {0, 1}, {1, 1}, {2, 1}, {3, 1},
            {-2, 2}, {-1, 2}, {0, 2}, {1, 2}, {2, 2},
            {-2, -1}, {-1, -1}, {0, -1}, {1, -1}, {2, -1},
            {-1, 3}, {0, 3}, {1, 3},
            {-1, -2}, {0, -2}, {1, -2}
        };

        for (int[] offset : dirtLayout) {
            BlockPos pos = center.add(offset[0], 0, offset[1]);
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            // Grass on top
            world.setBlockState(pos.up(), Blocks.GRASS_BLOCK.getDefaultState());
        }

        // === GRASS TOP LAYER ===
        // Already done above (grass on top of dirt)

        // === COBBLESTONE BOTTOM (under island) ===
        int[][] cobbleLayout = {
            {-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {2, 0},
            {-1, 1}, {0, 1}, {1, 1},
            {-1, -1}, {0, -1}, {1, -1}
        };

        for (int[] offset : cobbleLayout) {
            BlockPos pos = center.add(offset[0], -1, offset[1]);
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            BlockPos pos2 = center.add(offset[0], -2, offset[1]);
            world.setBlockState(pos2, Blocks.COBBLESTONE.getDefaultState());
        }

        // === OAK TREE (at center-left like Hypixel) ===
        BlockPos treeBase = center.add(-1, 1, 0);

        // Trunk (4 blocks tall)
        for (int i = 0; i < 4; i++) {
            world.setBlockState(treeBase.up(i), Blocks.OAK_LOG.getDefaultState());
        }

        // Leaves
        int[][] leafLayout = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {1, -1}, {-1, 1}, {1, 1}
        };

        // Layer 1 (y+3 from treeBase)
        for (int[] offset : leafLayout) {
            world.setBlockState(
                treeBase.add(offset[0], 3, offset[1]),
                Blocks.OAK_LEAVES.getDefaultState()
            );
        }

        // Layer 2 (y+4 from treeBase) - top crown
        world.setBlockState(treeBase.up(4), Blocks.OAK_LEAVES.getDefaultState());
        world.setBlockState(treeBase.add(1, 4, 0), Blocks.OAK_LEAVES.getDefaultState());
        world.setBlockState(treeBase.add(-1, 4, 0), Blocks.OAK_LEAVES.getDefaultState());
        world.setBlockState(treeBase.add(0, 4, 1), Blocks.OAK_LEAVES.getDefaultState());
        world.setBlockState(treeBase.add(0, 4, -1), Blocks.OAK_LEAVES.getDefaultState());
        world.setBlockState(treeBase.up(5), Blocks.OAK_LEAVES.getDefaultState());

        // === CHEST with starter items (next to tree) ===
        BlockPos chestPos = center.add(1, 1, 0);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState());

        // Fill chest with starter items
        if (world.getBlockEntity(chestPos) instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
            chest.setStack(0, new net.minecraft.item.ItemStack(net.minecraft.item.Items.PUMPKIN_SEEDS, 1));
            chest.setStack(1, new net.minecraft.item.ItemStack(net.minecraft.item.Items.MELON_SEEDS, 1));
            chest.setStack(2, new net.minecraft.item.ItemStack(net.minecraft.item.Items.SUGAR_CANE, 1));
            chest.setStack(3, new net.minecraft.item.ItemStack(net.minecraft.item.Items.OAK_SAPLING, 1));
            chest.setStack(4, new net.minecraft.item.ItemStack(net.minecraft.item.Items.ICE, 1));
            chest.setStack(5, new net.minecraft.item.ItemStack(net.minecraft.item.Items.LAVA_BUCKET, 1));
            chest.setStack(6, new net.minecraft.item.ItemStack(net.minecraft.item.Items.BONE, 2));
            chest.setStack(7, new net.minecraft.item.ItemStack(net.minecraft.item.Items.BREAD, 2));
        }

        // === SMALL COBBLESTONE ISLAND (for lava + water = cobble gen) ===
        BlockPos cobbIsland = center.add(6, 0, 0);
        world.setBlockState(cobbIsland, Blocks.COBBLESTONE.getDefaultState());
        world.setBlockState(cobbIsland.up(), Blocks.COBBLESTONE.getDefaultState());
        world.setBlockState(cobbIsland.add(1, 0, 0), Blocks.COBBLESTONE.getDefaultState());
        world.setBlockState(cobbIsland.add(1, 1, 0), Blocks.COBBLESTONE.getDefaultState());
        world.setBlockState(cobbIsland.add(2, 1, 0), Blocks.LAVA.getDefaultState());
        world.setBlockState(cobbIsland.add(-1, 1, 0), Blocks.WATER.getDefaultState());
    }
}
