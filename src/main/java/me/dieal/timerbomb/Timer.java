package me.dieal.timerbomb;

import me.dieal.timerbomb.bomb.listeners.Bomb;
import me.dieal.timerbomb.bomb.listeners.BombsManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {

    private Bomb bomb;
    private BombsManager manager;
    private int secondsLeft;

    public Timer (Bomb bomb, BombsManager manager) {
        this.manager = manager;
        this.bomb = bomb;
        this.secondsLeft = bomb.getSeconds();
        this.manager.addBomb(bomb);
    }

    @Override
    public void run () {

        if (bomb.isDefused()) {
            return;
        }

        bomb.setDisplayName(ChatColor.YELLOW + "" + secondsLeft);
        secondsLeft--;

        if (secondsLeft == -1) {
            bomb.explodeBomb();
            manager.removeBomb(bomb);
            this.cancel();
        }
    }

}
