package me.dieal.timerbomb;

import me.dieal.timerbomb.bomb.Bomb;
import me.dieal.timerbomb.bomb.BombsManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {

    private Bomb bomb;
    private BombsManager manager;

    public Timer (Bomb bomb, BombsManager manager) {
        this.manager = manager;
        this.bomb = bomb;
        this.manager.addBomb(bomb);
    }

    @Override
    public void run () {

        if (bomb.isDefused()) {
            return;
        }

        bomb.setDisplayName(ChatColor.YELLOW + "" + bomb.getSecondsLeft());
        bomb.decreaseSeconds();

        if (bomb.getSecondsLeft() == -1) {
            bomb.explodeBomb();
            manager.removeBomb(bomb);
            this.cancel();
        }

    }

}
