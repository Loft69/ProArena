package com.axon.proArena.object.selection;

import com.axon.proArena.object.selection.wrapper.SelectionPath;
import com.axon.proArena.object.selection.wrapper.SelectionWrapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class SelectionCommand implements CommandExecutor, TabCompleter {

    SelectionController controller;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command cmd,
            @NotNull String s,
            @NotNull String[] args)
    {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 2) {
            String command = args[0];

            if (command.equalsIgnoreCase("save")) {
                String path = args[1];
                SelectionType type = SelectionType.SAVE;
                SelectionPath wrapper = new SelectionPath(player, type, path, null);
                controller.apply(wrapper);
            }

            if (command.equalsIgnoreCase("set")){
                String subCommand = args[1];

                Block block = player.getTargetBlock(null, 10);
                if (block.getType() == Material.AIR) return false;
                SelectionType type;

                switch (subCommand) {
                    case "center" -> type = SelectionType.MIDDLE;
                    case "min" -> type = SelectionType.LEFT;
                    case "max" -> type = SelectionType.RIGHT;
                    default -> {
                        return false;
                    }
                }

                SelectionWrapper wrapper = new SelectionWrapper(player, type, block);
                controller.apply(wrapper);
            }
        }
        if (args.length == 1) {
            String command = args[0];
            switch (command) {
                case "clear" -> {
                    SelectionType type = SelectionType.CLEAR;
                    SelectionWrapper wrapper = new SelectionWrapper(player, type, null);
                    controller.apply(wrapper);
                }
                case "stick" -> {
                    ItemStack item = ItemStack.of(Material.STICK);
                    PersistentDataContainer pcd = item.getPersistentDataContainer()
                            .getAdapterContext()
                            .newPersistentDataContainer();
                    pcd.set(controller.getNamespacedKey(), PersistentDataType.STRING, "magic");

                    item.editMeta(itemMeta -> itemMeta.displayName(Component.text("Волшебная палочка").color(NamedTextColor.GOLD)));

                    player.getInventory().addItem(item).forEach((x, y) -> player.getWorld().dropItem(player.getLocation(), y));
                }
                case "paste" -> controller.placeSelection(player);
            }

        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command cmd,
            @NotNull String s,
            @NotNull String[] args
    )
    {
        if (args.length == 1) return List.of("set", "clear", "save", "stick");
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) return List.of("center", "min", "max");
        return List.of("");
    }
}
