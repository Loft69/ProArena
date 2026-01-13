package com.axon.proArena.object.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlockInstruction {
    @Getter
    String data;
    int dx;
    int dy;
    int dz;

    public void complete(int x, int y, int z, World world) {
        Block block = world.getBlockAt(x + dx, y + dy, z + dz);
        block.setBlockData(Bukkit.createBlockData(data));
    }

}
