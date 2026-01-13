package com.axon.proArena.object;

import com.axon.proArena.object.dto.StructureInstruction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Structure {

    StructureInstruction structureInstruction;

    public void place(@NonNull Location rawLocation) {
        structureInstruction.complete(rawLocation);
    }

}
