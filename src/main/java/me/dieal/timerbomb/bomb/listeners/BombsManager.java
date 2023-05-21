package me.dieal.timerbomb.bomb.listeners;

import org.bukkit.Location;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.ArrayList;

public class BombsManager implements Serializable {

    private final ArrayList<Bomb> bombs;
    private transient final String savePath;


    public BombsManager(String savePath) {
        this.savePath = savePath;
        BombsManager data = loadData(savePath);
        if (data == null) {
            this.bombs = new ArrayList<>();
        } else {
            this.bombs = data.getBombs();
            for (Bomb b : bombs) b.resetHologram();
        }
    }

    public ArrayList<Bomb> getBombs() {
        return new ArrayList<>(bombs);
    }

    public void addBomb(Bomb bomb) {
        if (bombs == null) return;
        if (bombs.contains(bomb)) return;
        bombs.add(bomb);
        saveData(savePath);
    }

    public void removeBomb(Bomb bomb) {
        if (bomb == null) return;
        if (!bombs.contains(bomb)) return;
        bombs.remove(bomb);
        saveData(savePath);
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

    private boolean saveData(String filePath) {

        FileOutputStream fileOut = null;
        BukkitObjectOutputStream out = null;

        try {
            fileOut = new FileOutputStream (filePath);
            out = new BukkitObjectOutputStream(fileOut);

            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private BombsManager loadData (String filePath) {

        FileInputStream fileIn = null;
        BukkitObjectInputStream in = null;

        try {
            fileIn = new FileInputStream(filePath);
            in = new BukkitObjectInputStream(fileIn);

            BombsManager data = (BombsManager) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}