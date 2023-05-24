package me.dieal.timerbomb.bomb;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Bomb implements Serializable {

    private final int startSeconds;
    private int secondsLeft;
    private final float explosionPower;
    private final Location bombLocation;
    private String hologramText;
    private UUID hologramUniqueId;
    private transient ArmorStand hologram;
    private final String dateFormatter;
    private boolean defused = false;
    private String defuserName;
    private String defuseTime;

    public Bomb (int seconds, float explosionPower, Location bombLocation) {
        this.bombLocation = bombLocation;
        this.startSeconds = seconds;
        this.secondsLeft = startSeconds;
        this.explosionPower = explosionPower;
        this.hologram = createHologram();
        this.defuseTime = "";
        this.hologramText = "";
        this.defuserName = "";
        this.dateFormatter = "dd/MM/yyyy HH:mm";
    }

    public void resetHologram() {
        Bukkit.getEntity(hologramUniqueId).remove();
        this.hologram = createHologram();
        this.hologram.setCustomName(hologramText);
    }

    private ArmorStand createHologram() {
        Location hologramLocation = bombLocation.clone().add(0.5, 1, 0.5);
        ArmorStand hologram = (ArmorStand) bombLocation.getWorld().spawnEntity(hologramLocation, EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setGravity(false);
        hologram.setMarker(true);
        hologram.setSmall(true);
        hologram.setCustomNameVisible(true);

        this.hologramUniqueId = hologram.getUniqueId();
        return hologram;
    }

    // Getters
    public boolean isDefused() {
        return defused;
    }

    public int getStartSeconds() {
        return startSeconds;
    }

    public int getSecondsLeft() {
        return secondsLeft;
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
    public void decreaseSeconds () {
        this.secondsLeft -= 1;
    }

    public void setDisplayName(String name) {
        if (name == null) {
            return;
        }

        this.hologramText = name;
        this.hologram.setCustomName(name);
    }

    // Bomb Actions
    public void explodeBomb() {
        removeBomb();
        bombLocation.getWorld().createExplosion(bombLocation, explosionPower, false, true);
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
        System.out.println(bombLocation);
        bombLocation.getBlock().setType(Material.AIR);
        bombLocation.getWorld().spawnParticle(Particle.BLOCK_CRACK, bombLocation.add(0.5, 0.5, 0.5), 50, data);
        bombLocation.getWorld().playSound(bombLocation, Sound.BLOCK_STONE_BREAK, 5, 1);
        hologram.remove();
    }

}