package com.axon.proArena.object.structure.impl;

import com.axon.proArena.object.structure.api.Structure;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class InstructionStructure implements Structure {

    String name;
    StructureInstruction structureInstruction;

    @Override
    public String name() {
        return name;
    }

    @Override
    public void place(@NonNull Location rawLocation) {
        structureInstruction.complete(rawLocation);
    }

    @Override
    public boolean isRotatable() {
        return structureInstruction.isRotatable();
    }

}
