package me.dieal.timerbomb.bomb.listeners;

import me.dieal.timerbomb.TimerBomb;
import me.dieal.timerbomb.defuse.inventories.BombDefusedInventory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;


public class BombInteractListener implements Listener {

    private static transient final long serialVersionUID = -1681012206529286330L;
    private TimerBomb plugin;

    private BombsManager manager;

    public BombInteractListener (TimerBomb plugin, BombsManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onBombInteract (PlayerInteractEvent e) {

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock().getType() == Material.AIR) return;
        if (!(e.getClickedBlock().getType() == Material.CRYING_OBSIDIAN));
        if (!(e.getHand() == EquipmentSlot.HAND)) return;

        Location bombLocation = e.getClickedBlock().getLocation();
        if (!(manager.isBomb(bombLocation))) {
            return;
        }

        Bomb bomb = manager.getBomb(bombLocation);
        Player player = e.getPlayer();

        if (!bomb.isDefused()) {
            bomb.defuseBomb(player);
        } else {
            BombDefusedInventory inventory = new BombDefusedInventory(bomb);
            inventory.openInventory(player);
        }

    }

}
