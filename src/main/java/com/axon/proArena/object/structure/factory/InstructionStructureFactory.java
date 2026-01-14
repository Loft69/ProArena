package com.axon.proArena.object.structure.factory;

import com.axon.proArena.object.structure.impl.StructureInstruction;
import com.axon.proArena.object.structure.FactoryType;
import com.axon.proArena.object.structure.api.Structure;
import com.axon.proArena.object.structure.api.StructureFactory;
import com.axon.proArena.object.structure.impl.InstructionStructure;
import lombok.NonNull;

public final class InstructionStructureFactory implements StructureFactory {

    @Override
    public @NonNull FactoryType type() {
        return FactoryType.INSTRUCTION;
    }

    @Override
    public Structure create(String name, StructureInstruction instruction) {
        return new InstructionStructure(name, instruction);
    }

}
