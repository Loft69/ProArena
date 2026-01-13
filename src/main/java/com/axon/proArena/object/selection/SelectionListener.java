package com.axon.proArena.object.selection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
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
        controller.deleteSelection(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!(action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();


    }

}
