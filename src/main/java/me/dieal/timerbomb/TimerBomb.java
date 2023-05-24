package me.dieal.timerbomb;

import me.dieal.timerbomb.bomb.BombsManager;
import me.dieal.timerbomb.commands.BombGiveCommand;
import me.dieal.timerbomb.bomb.listeners.BombInteractListener;
import me.dieal.timerbomb.bomb.listeners.BombPlaceListener;
import me.dieal.timerbomb.defuse.listeners.BombDefusedListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TimerBomb extends JavaPlugin {

    @Override
    public void onEnable() {
        BombsManager manager = new BombsManager("bombs.dat");
        registerEvents(manager);
        registerCommands(manager);
    }

    private void registerEvents(BombsManager manager) {
        getServer().getPluginManager().registerEvents(new BombPlaceListener(this, manager), this);
        getServer().getPluginManager().registerEvents(new BombInteractListener(this, manager), this);
        getServer().getPluginManager().registerEvents(new BombDefusedListener(manager), this);
    }

    private void registerCommands (BombsManager manager) {
        getCommand("getbomb").setExecutor(new BombGiveCommand());
    }

}
