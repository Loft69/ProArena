package com.axon.proArena.object.structure.impl;

import com.axon.proArena.object.structure.api.Category;
import com.axon.proArena.object.structure.api.Structure;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InstructionCategory implements Category {
    String name;
    List<Structure> structures = new ArrayList<>();

    @Override
    public void add(@NonNull Structure structure) {
        structures.add(structure);
    }

    @Override
    public List<Structure> getStructures() {
        return Collections.unmodifiableList(structures);
    }

    @Override
    public String name() {
        return name;
    }
}
