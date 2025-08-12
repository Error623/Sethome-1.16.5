package ru.example.sethome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HomesCommand implements CommandExecutor, Listener {

    private final SQLite database;
    private final Sethome plugin;

    public HomesCommand(SQLite database, Sethome plugin) {
        this.database = database;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков");
            return true;
        }

        Player player = (Player) sender;
        List<SQLite.HomeData> homes = database.getHomes(player.getUniqueId().toString());

        if (homes.isEmpty()) {
            player.sendMessage(ChatColor.YELLOW + "У вас нет точек дома");
            return true;
        }

        int size = ((homes.size() - 1) / 9 + 1) * 9;
        Inventory inv = Bukkit.createInventory(null, size, "Твои дома");

        for (SQLite.HomeData home : homes) {
            ItemStack item = new ItemStack(Material.RED_BED);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + home.homeName);

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "X: " + home.x);
            lore.add(ChatColor.GRAY + "Y: " + home.y);
            lore.add(ChatColor.GRAY + "Z: " + home.z);
            meta.setLore(lore);

            item.setItemMeta(meta);
            inv.addItem(item);
        }

        player.openInventory(inv);
        return true;
    }

    @EventHandler
    public void onInventoryClicked(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals("Твои дома")) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR || !clicked.hasItemMeta()) return;

        String homeName = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
        List<SQLite.HomeData> homes = database.getHomes(player.getUniqueId().toString());

        for (SQLite.HomeData home : homes) {
            if (home.homeName.equals(homeName)) {
                Location loc = new Location(player.getWorld(), home.x, home.y, home.z);
                player.teleport(loc);
                player.sendMessage(ChatColor.GREEN + "Телепортирован в " + ChatColor.YELLOW + homeName);
                break;
            }
        }
    }
}
