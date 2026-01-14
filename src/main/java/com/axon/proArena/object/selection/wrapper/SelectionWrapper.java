package com.axon.proArena.object.selection.wrapper;

import com.axon.proArena.object.selection.SelectionType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SelectionWrapper {
    final @NonNull Player player;
    final @NonNull SelectionType type;
    final Block block;

    public SelectionWrapper(@NonNull Player player, @NonNull SelectionType type, Block block) {
        this.player = player;
        this.type = type;
        this.block = block;
    }
}
