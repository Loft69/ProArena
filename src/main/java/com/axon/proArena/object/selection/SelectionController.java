package com.axon.proArena.object.selection;

import com.axon.proArena.object.structure.impl.InstructionStructure;
import com.axon.proArena.object.structure.impl.StructureInstruction;
import com.axon.proArena.object.selection.wrapper.SelectionPath;
import com.axon.proArena.object.selection.wrapper.SelectionWrapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SelectionController {

    JavaPlugin instance;
    HashMap<Player, Selection> selections = new HashMap<>();
    @Getter
    NamespacedKey namespacedKey = new NamespacedKey("myplugin", "stick_magic");

    SelectionCommand command = new SelectionCommand(this);
    SelectionListener listener = new SelectionListener(this);

    public void load() {
        registerCommand();
        registerListener();
    }

    private void registerCommand() {
        PluginCommand pluginCommand = instance.getCommand("proarena");
        if (pluginCommand == null) throw new RuntimeException();
        pluginCommand.setExecutor(command);
    }

    private void registerListener() {
        Bukkit.getPluginManager().registerEvents(listener, instance);
    }

    private Selection createSelection(@NonNull Player player) {
        Selection selection = new Selection();
        selections.put(player, selection);
        return selection;
    }

    private Selection getSelection(@NonNull Player player) {
        Selection selection = selections.getOrDefault(player, null);
        if (selection == null) return createSelection(player);
        return selection;
    }

    private void setSelectionCenter(@NonNull Player player, @NonNull Block block) {
        Selection selection = getSelection(player);
        selection.setCenter(block.getLocation());
    }

    private void setSelectionPos1(@NonNull Player player, @NonNull Block block) {
        Selection selection = getSelection(player);
        selection.setPos1(block.getLocation());
    }

    private void setSelectionPos2(@NonNull Player player, @NonNull Block block) {
        Selection selection = getSelection(player);
        selection.setPos2(block.getLocation());
    }

    private void clearSelection(@NonNull Player player) {
        Selection selection = getSelection(player);
        selection.clear();
    }

    private void saveSelection(@NonNull Player player, @NonNull String path) throws IOException {
        Selection selection = getSelection(player);
        selection.save(Path.of(path));
    }

    private void deleteSelection(@NonNull Player player) {
        selections.remove(player);
    }

    public void placeSelection(@NonNull Player player) {
        Selection selection = getSelection(player);
        if (!selection.isValid()) {
            player.sendMessage(" Чето пошло не так");
            return;
        }

        StructureInstruction structureInstruction = selection.toInstruction();
        InstructionStructure instructionStructure = new InstructionStructure("null", structureInstruction);
        instructionStructure.place(player.getLocation());
    }

    public void apply(@NonNull SelectionWrapper wrapper) {
        SelectionType type = wrapper.getType();
        Player player = wrapper.getPlayer();

        switch (type) {
            case SelectionType.DELETE -> deleteSelection(player);
            case SelectionType.CLEAR -> clearSelection(player);
            case SelectionType.SAVE -> {
                if (wrapper instanceof SelectionPath path) {
                    try {
                        saveSelection(player,"structure/" + path.getPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            default -> applyWithBlock(wrapper);
        }
        sendStatusMessage(player);
    }

    private void applyWithBlock(@NonNull SelectionWrapper wrapper) {
        SelectionType type = wrapper.getType();
        Player player = wrapper.getPlayer();
        Block block = wrapper.getBlock();

        if (block == null) throw new RuntimeException();

        switch (type) {
            case SelectionType.LEFT -> setSelectionPos1(player, wrapper.getBlock());
            case SelectionType.RIGHT -> setSelectionPos2(player, wrapper.getBlock());
            case SelectionType.MIDDLE -> setSelectionCenter(player, wrapper.getBlock());
        }
    }

    private void sendStatusMessage(@NonNull Player player) {
        Selection selection = getSelection(player);
        Component message = SelectionStatusComponent.build(selection.getPos1(), selection.getPos2(), selection.getCenter());
        player.sendMessage(message);
    }
}
