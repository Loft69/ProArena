package com.axon.proArena;

import com.axon.proArena.object.selection.SelectionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ProArena extends JavaPlugin implements Listener {

    SelectionController selectionController = new SelectionController(this);

    @Override
    public void onEnable() {
        selectionController.load();

        Bukkit.getPluginManager().registerEvents(this, this);

        getCommand("rere").setExecutor((sender, command, s, args) -> {
            if (args.length == 2) {
                String cmd = args[0];
                String worldName = args[1];

                if (cmd.equals("create")) {

                    WorldCreator creator = new WorldCreator(worldName);
                    creator.generator(new MyChunkGenerator());
                    creator.biomeProvider(new MyBiomeProvider());

                    World world = Bukkit.createWorld(creator);
                    if (world == null) return false;

                    world.setSpawnLocation(new Location(world, 0, 100, 0));
                    world.setGameRule(GameRule.DO_MOB_SPAWNING, false);

                    WorldBorder border = world.getWorldBorder();
                    border.setCenter(0, 0);
                    border.setSize(1024);
                    border.setWarningDistance(0);
                    border.setDamageAmount(10.0);
                    border.setDamageBuffer(0);

                    if (sender instanceof Player player) {
                        player.teleport(world.getSpawnLocation());
                        return true;
                    }
                    return false;
                }
                return false;
            } else if (args.length == 1) {
                String cmd = args[0];
                World world = Bukkit.getWorld(cmd);
                if (world != null && sender instanceof Player player) {
                    player.teleport(world.getSpawnLocation());
                    return true;
                }
                return false;
            }

            return true;
        });
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        if (!e.isNewChunk()) return;

        Chunk chunk = e.getChunk();
        World world = chunk.getWorld();
        Random random = new Random(chunk.getX() * 341873128712L + chunk.getZ());

        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);

            int y = world.getHighestBlockYAt(
                    chunk.getX() * 16 + x,
                    chunk.getZ() * 16 + z
            );

            if (random.nextDouble() < 0.3) {
                TreeGenerator.generate(
                        world,
                        chunk.getX() * 16 + x,
                        y,
                        chunk.getZ() * 16 + z
                );
            }
        }
    }

}
