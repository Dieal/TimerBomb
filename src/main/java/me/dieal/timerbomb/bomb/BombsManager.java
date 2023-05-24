package me.dieal.timerbomb.bomb;

import org.bukkit.Location;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.ArrayList;

public class BombsManager {

    private final ArrayList<Bomb> bombs;
    private final String savePath;


    public BombsManager(String savePath) {
        this.savePath = savePath;
        ArrayList<Bomb> data = loadData(savePath);
        this.bombs = data;

        if (!data.isEmpty()) {
            for (Bomb b : bombs) {
                b.resetHologram();
            }
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

            for (Bomb b : bombs) {
                out.writeObject(b);
            }

            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private ArrayList<Bomb> loadData (String filePath) {

        FileInputStream fileIn = null;
        BukkitObjectInputStream in = null;
        ArrayList<Bomb> result = new ArrayList<>();
        boolean eof = false;

        try {
            fileIn = new FileInputStream(filePath);
            in = new BukkitObjectInputStream(fileIn);

            while (!eof) {
                result.add((Bomb) in.readObject());
            }

            in.close();
            return result;

        } catch (EOFException end) {
            eof = true;
        } catch (FileNotFoundException fe) {
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}