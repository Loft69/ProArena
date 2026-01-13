package com.axon.proArena.object.selection;

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
        System.out.println(1);
        Action action = event.getAction();
        if (!(action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK)) return;

        Block block = event.getClickedBlock();
        if (block == null) return;
        System.out.println(2);

        Player player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (!mainHand.getType().equals(Material.STICK)) return;
//        if (!mainHand.getPersistentDataContainer().has(controller.getNamespacedKey(), PersistentDataType.STRING)) return;
        System.out.println(3);
        SelectionType type;
        if (action == Action.RIGHT_CLICK_BLOCK) type = SelectionType.RIGHT;
        else type = SelectionType.LEFT;

        System.out.println(4);

        SelectionWrapper wrapper = new SelectionWrapper(player, type, block);
        controller.apply(wrapper);
        event.setCancelled(true);
        System.out.println(5);
    }

}
