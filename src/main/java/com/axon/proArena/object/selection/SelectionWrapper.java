package com.axon.proArena.object.selection;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SelectionWrapper {
    final Player player;
    final SelectionType type;
    final Block block;
    @Setter
    String path;

    public SelectionWrapper(@NonNull Player player, @NonNull SelectionType type, Block block) {
        this.player = player;
        this.type = type;
        this.block = block;
    }
}
