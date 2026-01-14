package com.axon.proArena.object.structure.api;

import lombok.NonNull;

import java.util.List;

public interface Category {
    String name();
    void add(@NonNull Structure structure);
    List<Structure> getStructures();
}
