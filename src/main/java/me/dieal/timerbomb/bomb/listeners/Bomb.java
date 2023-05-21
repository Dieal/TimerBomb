package me.dieal.timerbomb.bomb.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Bomb {

    private int seconds;
    private final float exposionPower;
    private final Location bombLocation;
    private final ArmorStand hologram;
    private boolean defused = false;

    public Bomb (int seconds, float exposionPower, Location bombLocation) {
        this.seconds = seconds;
        this.exposionPower = exposionPower;
        this.bombLocation = bombLocation;
        this.hologram = createHologram();
    }

    private ArmorStand createHologram() {
        Location hologramLocation = new Location(bombLocation.getWorld(), bombLocation.getX() + 0.5, bombLocation.getY() + 0.85, bombLocation.getBlockZ() + 0.5);

        ArmorStand hologram = (ArmorStand) bombLocation.getWorld().spawnEntity(hologramLocation, EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setGravity(false);
        hologram.setMarker(true);
        hologram.setSmall(true);
        hologram.setCustomNameVisible(true);

        return hologram;
    }

    // Getters
    public boolean isDefused() {
        return defused;
    }

    public int getSeconds() {
        return seconds;
    }

    public ArmorStand getHologram() {
        return hologram;
    }

    public float getExposionPower() {
        return exposionPower;
    }

    public Location getBombLocation() {
        return bombLocation;
    }

    //Setters

    public void setDisplayName(String name) {
        if (name == null) {
            return;
        }

        this.hologram.setCustomName(name);
    }

    // Bomb Actions
    public void explodeBomb() {
        bombLocation.getWorld().createExplosion(bombLocation, exposionPower, false, true);
        removeBomb();
    }

    public void defuseBomb () {
        hologram.setCustomName(ChatColor.GREEN + "Bomb Defused");
        defused = true;
    }

    public void removeBomb() {
        hologram.remove();
        bombLocation.getBlock().setType(Material.AIR);
    }

}
