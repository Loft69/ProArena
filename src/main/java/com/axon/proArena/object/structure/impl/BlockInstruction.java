package com.axon.proArena.object.structure.impl;

import com.axon.proArena.object.structure.api.Instruction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlockInstruction implements Instruction {
    @Getter
    String data;
    int dx;
    int dy;
    int dz;

    @Override
    public void complete(int x, int y, int z, World world) {
        Block block = world.getBlockAt(x + dx, y + dy, z + dz);
        block.setBlockData(Bukkit.createBlockData(data));
    }

    @Override
    public int dx() {
        return dx;
    }

    @Override
    public int dy() {
        return dy;
    }

    @Override
    public int dz() {
        return dz;
    }

    @Override
    public String data() {
        return data;
    }
}
