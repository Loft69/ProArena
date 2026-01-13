package com.axon.proArena;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MyChunkGenerator extends ChunkGenerator {

    private final SimplexNoiseGenerator noise =
            new SimplexNoiseGenerator(12345);

    @Override
    public void generateNoise(
            @NotNull WorldInfo worldInfo,
            @NotNull Random random,
            int chunkX, int chunkZ,
            @NotNull ChunkData data
    ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;

                if (Math.abs(worldX) > 512 || Math.abs(worldZ) > 512) {
                    return; // не генерируем блоки
                }

                double n = noise.noise(worldX * 0.005, worldZ * 0.005);
                int height = -32 + (int) (n * 8); // холмы

                for (int y = -64; y <= height; y++) {
                    if (y == height) {
                        if (y < -35) data.setBlock(x, y, z, Material.SAND);
                        else data.setBlock(x, y, z, Material.GRASS_BLOCK);
                    } else if (y > height - 4){
                        data.setBlock(x, y, z, Material.DIRT);
                    } else if (y == -64) {
                        data.setBlock(x, y, z, Material.BEDROCK);
                    } else {
                        data.setBlock(x, y, z, Material.STONE);
                    }
                }

                // вода
                if (height < -35) {
                    for (int y = height + 1; y <= -35; y++) {
                        data.setBlock(x, y, z, Material.WATER);
                    }
                }
            }
        }
    }
}

