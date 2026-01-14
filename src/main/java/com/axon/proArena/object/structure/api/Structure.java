package com.axon.proArena.object.structure.api;

import lombok.NonNull;
import org.bukkit.Location;

public interface Structure {
    String name();
    void place(@NonNull Location location);
    boolean isRotatable();
}
