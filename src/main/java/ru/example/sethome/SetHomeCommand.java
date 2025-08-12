package ru.example.sethome;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {
    private final Sethome plugin;

    public SetHomeCommand(Sethome plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.YELLOW + "Используй: /sethome <название>");
            return true;
        }

        String uuid = player.getUniqueId().toString();
        String homeName = args[0]; // берём название из команды
        Location loc = player.getLocation();

        // сохраняем X, Y, Z в правильном порядке
        plugin.getDatabase().insertHome(
                uuid,
                homeName,
                loc.getX(),
                loc.getY(),
                loc.getZ()
        );

        player.sendMessage(ChatColor.GREEN + "Точка дома '" + ChatColor.YELLOW + homeName + ChatColor.GREEN + "' сохранена!");
        return true;
    }
}
