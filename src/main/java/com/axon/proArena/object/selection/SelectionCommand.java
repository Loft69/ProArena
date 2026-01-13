package com.axon.proArena.object.selection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class SelectionCommand implements CommandExecutor {

    SelectionController controller;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


        return false;
    }

}
