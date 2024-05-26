package org.sabbelsocke.randomtp.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class LocationGenerator {

    private static final Set<Material> UNSAFE_MATERIALS = new HashSet<>();

    static {
        UNSAFE_MATERIALS.add(Material.ACACIA_LEAVES);
        UNSAFE_MATERIALS.add(Material.AZALEA_LEAVES);
        UNSAFE_MATERIALS.add(Material.BIRCH_LEAVES);
        UNSAFE_MATERIALS.add(Material.DARK_OAK_LEAVES);
        UNSAFE_MATERIALS.add(Material.FLOWERING_AZALEA_LEAVES);
        UNSAFE_MATERIALS.add(Material.JUNGLE_LEAVES);
        UNSAFE_MATERIALS.add(Material.OAK_LEAVES);
        UNSAFE_MATERIALS.add(Material.SPRUCE_LEAVES);
        UNSAFE_MATERIALS.add(Material.CACTUS);
        UNSAFE_MATERIALS.add(Material.FIRE);
        UNSAFE_MATERIALS.add(Material.LAVA);
        UNSAFE_MATERIALS.add(Material.LAVA_CAULDRON);
        UNSAFE_MATERIALS.add(Material.MAGMA_BLOCK);
        UNSAFE_MATERIALS.add(Material.WATER);
    }

    public static @NotNull CompletableFuture<Location> generateLocationAsync(Plugin plugin, World world) {
        return CompletableFuture.supplyAsync(() -> {
            Random random = new Random();
            int xlimit = (int) (world.getWorldBorder().getSize() / 2);
            int zlimit = (int) (world.getWorldBorder().getSize() / 2);

            while (true) {
                int x = random.nextInt(xlimit * 2) - xlimit;
                int z = random.nextInt(zlimit * 2) - zlimit;

                Block block = world.getHighestBlockAt(x, z);
                Block higherBlock = world.getBlockAt(x, block.getY() + 2, z);

                if (!UNSAFE_MATERIALS.contains(block.getType()) && !UNSAFE_MATERIALS.contains(higherBlock.getType())) {
                    return new Location(world, x + 0.5, block.getY() + 1, z + 0.5);
                }
            }
        }, CompletableFuture.delayedExecutor(0, java.util.concurrent.TimeUnit.MILLISECONDS, Executors.newCachedThreadPool()));
    }
}
