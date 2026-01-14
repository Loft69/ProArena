package com.axon.proArena.object.structure.registry;

import com.axon.proArena.object.structure.impl.StructureInstruction;
import com.axon.proArena.object.structure.FactoryType;
import com.axon.proArena.object.structure.api.Structure;
import com.axon.proArena.object.structure.api.StructureFactory;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class StructureRegistry {

    static Map<FactoryType, StructureFactory> FACTORIES = new HashMap<>();

    public static void register(@NonNull StructureFactory factory) {
        FACTORIES.put(factory.type(), factory);
    }

    public static Structure create(FactoryType type, String name, StructureInstruction instruction) {
        StructureFactory factory = FACTORIES.get(type);
        if (factory == null) throw new IllegalStateException("Unknown structure type: " + type);

        return factory.create(name, instruction);
    }
}
