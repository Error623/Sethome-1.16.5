package ru.example.sethome;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getLogger;

public final class Sethome extends JavaPlugin {

    private SQLite database;

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.DARK_GREEN + "SetHome is Enabled");

        database = new SQLite(this);
        database.connect();
        database.createTable();

        // 2+2=
        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        this.getCommand("delhome").setExecutor(new DelHomeCommand(database));
        HomesCommand homesCommand = new HomesCommand(database, this);
        this.getCommand("homes").setExecutor(homesCommand);
        getServer().getPluginManager().registerEvents(homesCommand, this);

    }

    @Override
    public void onDisable() { // название public подчёркнуто и серое
        getLogger().info(ChatColor.DARK_BLUE + "Sethome is Disabled");
    }

    public SQLite getDatabase() {
        return database;

    }
}



