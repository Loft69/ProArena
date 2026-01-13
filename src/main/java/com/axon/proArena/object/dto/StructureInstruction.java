package com.axon.proArena.object.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;

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
        for (BlockInstruction blockInstruction : instructions) blockInstruction.complete(rawLocation);
    }

    private boolean isValid(@NonNull BlockInstruction blockInstruction) {
        return blockInstruction.data() != null;
    }

    @Override
    public boolean isRotatable() {
        return rotatable;
    }
}
