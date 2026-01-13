package com.axon.proArena.object.selection;

import com.axon.proArena.object.dto.BlockInstruction;
import com.axon.proArena.object.dto.StructureInstruction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Selection {
    Location center;
    Location min;
    Location max;
    boolean rotatable = false;

    public StructureInstruction toInstruction() {
        StructureInstruction structure = new StructureInstruction();

        World world = center.getWorld();
        if (world == null) return null;

        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();

        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {

                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType().isAir()) continue;

                    int dx = x - cx;
                    int dy = y - cy;
                    int dz = z - cz;

                    BlockData blockData = block.getBlockData();

                    structure.add(new BlockInstruction(blockData, dx, dy, dz));
                }
            }
        }
        return structure;
    }

    public void save(@NonNull Path path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StructureInstruction structure = toInstruction();

        Files.createDirectories(path.getParent());
        Files.writeString(path, gson.toJson(structure), StandardCharsets.UTF_8);
    }

    public void clear() {
        this.center = null;
        this.min = null;
        this.max = null;
        this.rotatable = false;
    }

}
