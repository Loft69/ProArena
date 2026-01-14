package com.axon.proArena.object.selection.wrapper;

import com.axon.proArena.object.selection.SelectionType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SelectionPath extends SelectionWrapper{

    String path;

    public SelectionPath(@NonNull Player player, @NonNull SelectionType type, @NonNull String path, Block block) {
        super(player, type, block);
        this.path = path;
    }
}
