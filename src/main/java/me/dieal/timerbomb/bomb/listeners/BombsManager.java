package me.dieal.timerbomb.bomb.listeners;

import org.bukkit.Location;

import java.util.ArrayList;

public class BombsManager {

    ArrayList<Bomb> bombs;

    public BombsManager() {
        this.bombs = new ArrayList<>();
    }

    public void addBomb(Bomb bomb) {
        if (bombs == null) return;
        if (bombs.contains(bomb)) return;
        bombs.add(bomb);
    }

    public void removeBomb(Bomb bomb) {
        if (bomb == null) return;
        if (!bombs.contains(bomb)) return;
        bombs.remove(bomb);
    }

    public Bomb getBomb(Location location) {
        if (location == null) return null;
        for (Bomb b : bombs) {
            if (b.getBombLocation().equals(location)) {
                return b;
            }
        }
        return null;
    }

    public boolean isBomb(Location location) {
        for (Bomb b : bombs) {
            if (b.getBombLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

}