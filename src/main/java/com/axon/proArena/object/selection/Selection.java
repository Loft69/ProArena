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
    Location pos1;
    Location pos2;
    boolean rotatable = false;

    public StructureInstruction toInstruction() {
        if (!isValid()) return null;
        StructureInstruction structure = new StructureInstruction();

        World world = center.getWorld();
        if (world == null) return null;

        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();

        Location min = calculate(CalculateType.MIN);
        Location max = calculate(CalculateType.MAX);
        assert max != null && min != null;

        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {

                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType().isAir()) continue;

                    int dx = x - cx;
                    int dy = y - cy;
                    int dz = z - cz;

                    BlockData blockData = block.getBlockData();

                    structure.add(new BlockInstruction(blockData.getAsString(), dx, dy, dz));
                }
            }
        }
        return structure;
    }

    public void save(@NonNull Path path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StructureInstruction structure = toInstruction();

        if (structure == null) throw new IllegalStateException("StructureInstruction is null");

        Files.createDirectories(path.getParent());
        Files.writeString(path, gson.toJson(structure), StandardCharsets.UTF_8);
    }

    public void clear() {
        this.center = null;
        this.pos1 = null;
        this.pos2 = null;
        this.rotatable = false;
    }

    public boolean isValid() {
        return this.center != null && this.pos1 != null && this.pos2 != null;
    }

    private Location calculate(CalculateType type) {
        switch (type) {
            case MAX -> {
                return new Location(pos1.getWorld(),
                        Math.max(pos1.getBlockX(), pos2.getBlockX()),
                        Math.max(pos1.getBlockY(), pos2.getBlockY()),
                        Math.max(pos1.getBlockZ(), pos2.getBlockZ())
                );
            }
            case MIN -> {
                return new Location(pos1.getWorld(),
                        Math.min(pos1.getBlockX(), pos2.getBlockX()),
                        Math.min(pos1.getBlockY(), pos2.getBlockY()),
                        Math.min(pos1.getBlockZ(), pos2.getBlockZ())
                );
            }
            default -> {
                return null;
            }
        }
    }

    enum CalculateType {
        MAX,
        MIN
    }

}
