package me.dieal.timerbomb.bomb.listeners;

import me.dieal.timerbomb.Timer;
import me.dieal.timerbomb.TimerBomb;
import me.dieal.timerbomb.bomb.Bomb;
import me.dieal.timerbomb.bomb.BombsManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BombPlaceListener implements Listener {

    private TimerBomb plugin;
    private BombsManager manager;

    public BombPlaceListener (TimerBomb plugin, BombsManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        ItemStack blockItem = e.getItemInHand();
        if (!blockItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Timer Bomb")) {
            return;
        }

        Block block = e.getBlock();
        Player player = e.getPlayer();
        Location blockLocation = block.getLocation();
        int seconds = 10;

        Bomb bomb = new Bomb(seconds, 10, blockLocation);
        Timer blockTimer = new Timer (bomb, manager);
        blockTimer.runTaskTimer(plugin, 0L, 20L);

    }

}
