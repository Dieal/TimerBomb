package me.dieal.timerbomb.defuse.inventories;

import me.dieal.timerbomb.bomb.Bomb;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BombDefusedInventory implements InventoryHolder {

    private Inventory inventory;
    private Bomb bomb;

    public BombDefusedInventory (Bomb bomb) {
        this.bomb = bomb;
        this.inventory = createInventory();
    }

    private Inventory createInventory () {

        Inventory inventory = Bukkit.createInventory(this, 27, "Bomb successfully defused");
        inventory.setItem(11, createReportItem());
        inventory.setItem(15, createRemoveItem());

        return inventory;

    }

    private ItemStack createReportItem () {

        ItemStack report = new ItemStack (Material.BOOK);

        ItemMeta reportMeta = report.getItemMeta();
        ArrayList<String> reportLore = new ArrayList<>();
        reportLore.add (ChatColor.WHITE + "Defused by " + bomb.getDefuserName());
        reportLore.add (ChatColor.WHITE + "Defused at " + bomb.getDefuseTime());
        reportLore.add ("");
        reportLore.add (ChatColor.YELLOW + "Click here to get a physical report");
        reportMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Defuse Report");
        reportMeta.setLore(reportLore);
        report.setItemMeta(reportMeta);

        return report;
    }

    private ItemStack createRemoveItem () {
        ItemStack remove = new ItemStack(Material.BARRIER);
        ItemMeta removeMeta = remove.getItemMeta();
        ArrayList<String> removeLore = new ArrayList<>();
        removeLore.add(ChatColor.YELLOW + "Click here to destroy the bomb");
        removeMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Remove Bomb");
        removeMeta.setLore(removeLore);
        remove.setItemMeta(removeMeta);

        return remove;
    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public Bomb getClickedBomb () {
        return bomb;
    }

    public void openInventory (Player player) {
        if (player == null) return;
        player.openInventory(inventory);
    }

}
