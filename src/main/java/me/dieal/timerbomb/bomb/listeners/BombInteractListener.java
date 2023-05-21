package me.dieal.timerbomb.listeners;

import me.dieal.timerbomb.Bomb;
import me.dieal.timerbomb.BombsManager;
import me.dieal.timerbomb.TimerBomb;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;


public class BombInteractListener implements Listener {

    private TimerBomb plugin;

    private BombsManager manager;

    public BombInteractListener (TimerBomb plugin, BombsManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onBombInteract (PlayerInteractEvent e) {

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!(e.getHand() == EquipmentSlot.HAND)) return;

        Location bombLocation = e.getClickedBlock().getLocation();
        if (!(manager.bombExists(bombLocation))) {
            return;
        }

        Bomb bomb = manager.getBomb(bombLocation);
        e.getPlayer().sendMessage("Defuse click");
        bomb.defuseBomb();

    }

}