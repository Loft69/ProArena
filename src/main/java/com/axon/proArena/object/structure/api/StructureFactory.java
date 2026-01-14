package com.axon.proArena.object.structure.api;

import com.axon.proArena.object.structure.impl.StructureInstruction;
import com.axon.proArena.object.structure.FactoryType;
import lombok.NonNull;

public interface StructureFactory {
    @NonNull FactoryType type();
    Structure create(String name, StructureInstruction instruction);
}
