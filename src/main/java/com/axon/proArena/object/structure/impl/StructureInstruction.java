package com.axon.proArena.object.structure.impl;

import com.axon.proArena.object.structure.api.StructureMeta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StructureInstruction implements StructureMeta {
    final List<BlockInstruction> instructions = new ArrayList<>();
    @Setter
    boolean rotatable = false;

    public List<BlockInstruction> getInstructions() {
        return Collections.unmodifiableList(instructions);
    }

    public void add(@NonNull BlockInstruction blockInstruction) {
        if (!isValid(blockInstruction)) return;
        instructions.add(blockInstruction);
    }

    public void complete(@NonNull Location rawLocation) {
        World world = rawLocation.getWorld();
        if (world == null) return;

        int x = rawLocation.getBlockX();
        int y = rawLocation.getBlockY();
        int z = rawLocation.getBlockZ();

        for (BlockInstruction blockInstruction : instructions) blockInstruction.complete(x, y, z, world);
    }

    private boolean isValid(@NonNull BlockInstruction blockInstruction) {
        return blockInstruction.getData() != null;
    }

    @Override
    public boolean isRotatable() {
        return rotatable;
    }
}
