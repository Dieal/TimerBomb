package me.dieal.timerbomb;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BombGiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        ItemStack bomb = new ItemStack(Material.CRYING_OBSIDIAN);
        ItemMeta bombMeta = bomb.getItemMeta();
        bombMeta.setDisplayName("Bomb");
        bomb.setItemMeta(bombMeta);
        player.getInventory().addItem(bomb);

        return true;
    }
}
