package me.dieal.timerbomb.defuse.listeners;

import me.dieal.timerbomb.bomb.Bomb;
import me.dieal.timerbomb.bomb.BombsManager;
import me.dieal.timerbomb.defuse.inventories.BombDefusedInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class BombDefusedListener implements Listener {

    private BombsManager manager;

    public BombDefusedListener (BombsManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onMenuClick (InventoryClickEvent e) {

        Inventory inv = e.getInventory();
        if (!(inv.getHolder() instanceof BombDefusedInventory)) return;
        if (e.getCurrentItem() == null) return;

        BombDefusedInventory menuHolder = (BombDefusedInventory) inv.getHolder();
        Player player = (Player) e.getWhoClicked();
        Bomb bomb = menuHolder.getClickedBomb();
        ItemStack clickedItem = e.getCurrentItem();
        e.setCancelled(true);

        switch (clickedItem.getType()) {
            case BOOK:
                player.closeInventory();
                player.getInventory().addItem(createReport(bomb));
                break;
            case BARRIER:
                player.closeInventory();
                bomb.removeBomb();
                manager.removeBomb(bomb);
                break;
        }

    }

    private ItemStack createReport (Bomb bomb) {

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        ArrayList<String> pages = new ArrayList<>();
        pages.add(createPage(bomb));
        meta.setTitle("Defuse " + bomb.getDefuseTime());
        meta.setPages(pages);
        meta.setAuthor("System");
        book.setItemMeta(meta);

        return book;

    }

    private String createPage(Bomb bomb) {
        StringBuilder builder = new StringBuilder();
        String title = ChatColor.DARK_RED + "" + ChatColor.BOLD + "Report" + ChatColor.BLACK;
        builder.append("       [ " + title + " ]");

        builder.append("\n\n " + ChatColor.RED + "Defuser:\n" + ChatColor.BLACK);
        builder.append(" " + bomb.getDefuserName());

        builder.append("\n\n " + ChatColor.RED + "Date and time:\n" + ChatColor.BLACK);
        builder.append(" " + bomb.getDefuseTime());

        return builder.toString();
    }

}
