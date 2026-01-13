package com.axon.proArena.object.dto;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public record BlockInstruction(BlockData data, int dx, int dy, int dz) {

    public void complete(@NonNull Location rawLocation) {
        Location location = rawLocation.clone();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        World world = location.getWorld();
        if (world == null) return;

        location.setX(x + dx);
        location.setY(y + dy);
        location.setZ(z + dz);

        Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (!block.getType().equals(Material.AIR)) block.setBlockData(data);
    }

}
