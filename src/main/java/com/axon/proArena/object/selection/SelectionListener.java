package com.axon.proArena.object.selection;

import com.axon.proArena.object.selection.wrapper.SelectionWrapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class SelectionListener implements Listener {

    SelectionController controller;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        SelectionType type = SelectionType.DELETE;
        SelectionWrapper wrapper = new SelectionWrapper(player, type, null);

        controller.apply(wrapper);
    }



    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!(action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK)) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Player player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (!mainHand.getType().equals(Material.STICK)) return;
//        if (!mainHand.getPersistentDataContainer().has(controller.getNamespacedKey(), PersistentDataType.STRING)) return;
        SelectionType type;
        if (action == Action.RIGHT_CLICK_BLOCK) type = SelectionType.RIGHT;
        else type = SelectionType.LEFT;


        SelectionWrapper wrapper = new SelectionWrapper(player, type, block);
        controller.apply(wrapper);
        event.setCancelled(true);
    }

}
