package me.dieal.timerbomb.defuse.listeners;

import me.dieal.timerbomb.bomb.listeners.Bomb;
import me.dieal.timerbomb.bomb.listeners.BombsManager;
import me.dieal.timerbomb.defuse.inventories.BombDefusedInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class BombDefusedListener implements Listener {

    private BombsManager manager;

    public BombDefusedListener (BombsManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onMenuClick (InventoryClickEvent e) {

        Inventory inv = e.getInventory();
        if (!(inv.getHolder() instanceof BombDefusedInventory)) return;
        if (e.getCurrentItem() == null) return;

        BombDefusedInventory menuHolder = (BombDefusedInventory) inv.getHolder();
        Player player = (Player) e.getWhoClicked();
        Bomb bomb = menuHolder.getClickedBomb();
        ItemStack clickedItem = e.getCurrentItem();
        e.setCancelled(true);

        switch (clickedItem.getType()) {
            case BOOK:
                // gives the report book to the player
                player.closeInventory();
                break;
            case BARRIER:
                player.closeInventory();
                bomb.removeBomb();
                manager.removeBomb(bomb);
                break;
        }

    }

}
