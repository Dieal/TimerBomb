package me.dieal.timerbomb;

import me.dieal.timerbomb.bomb.listeners.Bomb;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {

    private Bomb bomb;
    private int secondsLeft;

    public Timer (Bomb bomb) {
        this.bomb = bomb;
        this.secondsLeft = bomb.getSeconds();
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
            this.cancel();
        }
    }

}
