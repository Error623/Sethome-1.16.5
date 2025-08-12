package ru.example.sethome;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DelHomeCommand implements CommandExecutor {

    private final SQLite database;

    public DelHomeCommand(SQLite database) {
    this.database = database;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "команда только для игроков");
            return true;
        }
        if (strings.length != 1) {
           sender.sendMessage(ChatColor.YELLOW + "Используй: /delhome <название точки>");
           return true;
        }
        Player player = (Player) sender;
        String homeName = strings[0]; // args красный
        String uuid = player.getUniqueId().toString();

        boolean deleted = database.deleteHome(uuid, homeName);

        if (deleted) {
        player.sendMessage(ChatColor.GREEN + "Точка: " + homeName + " удалена");
        } else {
            player.sendMessage(ChatColor.RED + "Точка: " + homeName + " не найдена");
        }
        return true;
    }

}
