package com.axon.proArena.object.structure.api;

import org.bukkit.World;

public interface Instruction {
    int dx();
    int dy();
    int dz();
    String data();
    void complete(int x, int y, int z, World world);
}
