package me.dieal.timerbomb.bomb.listeners;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bomb implements Serializable {

    private final int seconds;
    private final float explosionPower;
    private final Location bombLocation;
    private transient ArmorStand hologram;
    private final String dateFormatter;
    private boolean defused = false;
    private String defuserName;
    private String defuseTime;

    public Bomb (int seconds, float explosionPower, Location bombLocation) {
        this.seconds = seconds;
        this.explosionPower = explosionPower;
        this.bombLocation = bombLocation;
        this.hologram = createHologram();
        this.defuseTime = "";
        this.defuserName = "";
        this.dateFormatter = "dd/MM/yyyy HH:mm";
    }

    public void resetHologram() {
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

    public float getExplosionPower() {
        return explosionPower;
    }

    public Location getBombLocation() {
        return bombLocation;
    }

    public String getDefuserName() {
        if (defuserName == null) return "";
        return defuserName;
    }

    public String getDefuseTime() {
        if (defuseTime.isEmpty()) return "";
        return defuseTime;
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
        bombLocation.getWorld().createExplosion(bombLocation, explosionPower, false, true);
        removeBomb();
    }

    public void defuseBomb (Player defuser) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatter);
        hologram.setCustomName(ChatColor.GREEN + "Bomb Defused");
        defuserName = defuser.getName();
        defuseTime = LocalDateTime.now().format(formatter);
        defused = true;
    }

    public void removeBomb() {
        BlockData data = bombLocation.getBlock().getBlockData();
        hologram.remove();
        bombLocation.getBlock().setType(Material.AIR);
        bombLocation.getWorld().spawnParticle(Particle.BLOCK_CRACK, bombLocation.add(0.5, 0.5, 0.5), 50, data);
        bombLocation.getWorld().playSound(bombLocation, Sound.BLOCK_STONE_BREAK, 5, 1);
    }

}