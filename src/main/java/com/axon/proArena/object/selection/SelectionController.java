package com.axon.proArena.object.selection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SelectionController {

    JavaPlugin instance;
    HashMap<Player, Selection> selections = new HashMap<>();
    NamespacedKey namespacedKey = new NamespacedKey("magic_stick", "stick_magic");

    SelectionCommand command = new SelectionCommand(this);
    SelectionListener listener = new SelectionListener(this);

    private void registerCommand() {
        PluginCommand pluginCommand = instance.getCommand("proarena");
        if (pluginCommand == null) throw new RuntimeException();
        pluginCommand.setExecutor(command);
    }

    private void registerListener() {
        Bukkit.getPluginManager().registerEvents(listener, instance);
    }

    public void load() {
        registerCommand();
        registerListener();
    }

    public Selection createSelection(@NonNull Player player) {
        return selections.put(player, new Selection());
    }

    private Selection getSelection(@NonNull Player player) {
        Selection selection = selections.getOrDefault(player, null);
        if (selection == null) return createSelection(player);
        return selection;
    }

    public void setSelectionCenter(@NonNull Player player, @NonNull Block block) {
        Selection selection = getSelection(player);
        selection.setCenter(block.getLocation());
    }

    public void setSelectionMin(@NonNull Player player, @NonNull Block block) {
        Selection selection = getSelection(player);
        selection.setMin(block.getLocation());
    }

    public void setSelectionMax(@NonNull Player player, @NonNull Block block) {
        Selection selection = getSelection(player);
        selection.setMax(block.getLocation());
    }

    public void clearSelection(@NonNull Player player) {
        Selection selection = getSelection(player);
        selection.clear();
    }

    public void deleteSelection(@NonNull Player player) {
        selections.remove(player);
    }
}
