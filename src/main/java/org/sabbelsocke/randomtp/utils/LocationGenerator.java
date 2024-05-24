package org.sabbelsocke.randomtp.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationGenerator {


    public static @NotNull Location generateLocationAsync(Plugin plugin, World world) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Location> callable = new Callable<Location>() {
            @Override
            public Location call() {
                List<String> unsafeMaterials = new ArrayList<String>();
                unsafeMaterials.add("ACACIA_LEAVES");
                unsafeMaterials.add("AZALEA_LEAVES");
                unsafeMaterials.add("BIRCH_LEAVES");
                unsafeMaterials.add("DARK_OAK_LEAVES");
                unsafeMaterials.add("FLOWERING_AZALEA_LEAVES");
                unsafeMaterials.add("JUNGLE_LEAVES");
                unsafeMaterials.add("OAK_LEAVES");
                unsafeMaterials.add("SPRUCE_LEAVES");
                unsafeMaterials.add("CACTUS");
                unsafeMaterials.add("FIRE");
                unsafeMaterials.add("LAVA");
                unsafeMaterials.add("LAVA_CAULDRON");
                unsafeMaterials.add("MAGMA_BLOCK");
                unsafeMaterials.add("WATER");

                int xlimit = (int) (world.getWorldBorder().getSize() / 2);
                int zlimit = (int) (world.getWorldBorder().getSize() / 2);

                Random random = new Random();
                boolean negx = random.nextBoolean();
                boolean negz = random.nextBoolean();
                while (true) {
                    int x = 0;
                    int z = 0;
                    if (negx) {
                        x = x - random.nextInt(xlimit);
                    } else {
                        x = x + random.nextInt(xlimit);
                    }

                    if (negz) {
                        z = z - random.nextInt(zlimit);
                    } else {
                        z = z + random.nextInt(zlimit);
                    }

                    Block block = world.getHighestBlockAt(x, z);
                    Block higherBlock = world.getBlockAt(x, block.getY() + 2, z);

                    if (unsafeMaterials.contains(block.getType().toString()) || unsafeMaterials.contains(higherBlock.getType().toString()))
                        continue;
                    return new Location(world, x + .5, block.getY() + 1, z + .5);
                }
            }
        };

        return executor.submit(callable).get();
    }

}
