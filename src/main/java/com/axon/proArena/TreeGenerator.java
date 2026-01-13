package com.axon.proArena;

import org.bukkit.Material;
import org.bukkit.World;

public class TreeGenerator {

    public static void generate(World world, int x, int y, int z) {

        if (Math.abs(x) > 512 && Math.abs(z) > 512) return;

        if (!world.getBlockAt(x -1, y, z).getType().equals(Material.GRASS_BLOCK)) return;

        // ствол    
        for (int i = 0; i < 5; i++) {
            world.getBlockAt(x, y + i, z)
                    .setType(Material.OAK_LOG);
        }

        // ветка
        world.getBlockAt(x + 1, y + 3, z)
                .setType(Material.OAK_LOG);

        // листья
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                world.getBlockAt(x + dx, y + 4, z + dz)
                        .setType(Material.OAK_LEAVES);
            }
        }
    }
}

