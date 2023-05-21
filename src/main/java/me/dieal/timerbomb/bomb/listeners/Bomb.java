package me.dieal.timerbomb.bomb.listeners;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bomb {

    private int seconds;
    private final float exposionPower;
    private final Location bombLocation;
    private final ArmorStand hologram;
    private boolean defused = false;
    private String defuserName;
    private LocalDateTime defuseTime;
    private DateTimeFormatter dateFormatter;

    public Bomb (int seconds, float exposionPower, Location bombLocation) {
        this.seconds = seconds;
        this.exposionPower = exposionPower;
        this.bombLocation = bombLocation;
        this.hologram = createHologram();
        this.defuseTime = null;
        this.defuserName = null;
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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

    public String getDefuserName() {
        if (defuserName == null) return "";
        return defuserName;
    }

    public String getDefuseTime() {
        if (defuseTime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return defuseTime.format(formatter);
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

    public void defuseBomb (Player defuser) {
        hologram.setCustomName(ChatColor.GREEN + "Bomb Defused");
        defuserName = defuser.getName();
        defuseTime = LocalDateTime.now();
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