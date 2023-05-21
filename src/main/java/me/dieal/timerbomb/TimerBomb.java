package me.dieal.timerbomb;

import me.dieal.timerbomb.bomb.listeners.BombsManager;
import me.dieal.timerbomb.commands.BombGiveCommand;
import me.dieal.timerbomb.bomb.listeners.BombInteractListener;
import me.dieal.timerbomb.bomb.listeners.BombPlaceListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TimerBomb extends JavaPlugin {

    @Override
    public void onEnable() {

        BombsManager manager = new BombsManager();
        getServer().getPluginManager().registerEvents(new BombPlaceListener(this, manager), this);
        getServer().getPluginManager().registerEvents(new BombInteractListener(this, manager), this);
        getCommand("getbomb").setExecutor(new BombGiveCommand());

    }

}
