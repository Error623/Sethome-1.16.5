package ru.example.sethome;

import org.bukkit.Bukkit;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLite {
    private Connection connection;
    private final Sethome plugin;

    public SQLite(Sethome plugin) {
        this.plugin = plugin;
    }

    // Подключение к базе
    public void connect() {
        try {
            // создаём папку плагина, если её нет
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }

            // путь к файлу базы в папке плагина
            File dbFile = new File(plugin.getDataFolder(), "homes.db");

            // подключение
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());

            // создаём таблицу, если её нет
            String createTableSQL = "CREATE TABLE IF NOT EXISTS homes (" +
                    "uuid TEXT, " +
                    "home_name TEXT, " +
                    "x REAL, " +
                    "y REAL, " +
                    "z REAL" +
                    ")";
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSQL);
            stmt.close();

            System.out.println("[SQLite] Database connected: " + dbFile.getAbsolutePath());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Создание таблицы
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS homes (" +
                "uuid TEXT," +
                "home_name TEXT," +
                "x REAL," +
                "y REAL," +
                "z REAL" +
                ")";

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
            System.out.println("[SQLite]: A table created or already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Вставка новой точки дома
    public void insertHome(String uuid, String homeName, double x, double y, double z) {
        String sql = "INSERT INTO homes(uuid, home_name, x, y, z) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ps.setString(2, homeName);
            ps.setDouble(3, x);
            ps.setDouble(4, y);
            ps.setDouble(5, z);
            ps.executeUpdate();
            System.out.println("[SQLite] Home added: " + homeName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean deleteHome(String uuid, String playerName, String homeName) {
        String checkSql = "SELECT COUNT(*) FROM homes WHERE uuid = ? AND home_name = ?";
        String deleteSql = "DELETE FROM homes WHERE uuid = ? AND home_name = ?";

        try {
            // Проверяем, есть ли точка
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setString(1, uuid);
            checkStmt.setString(2, homeName);
            ResultSet rs = checkStmt.executeQuery();

            boolean exists = rs.next() && rs.getInt(1) > 0;
            checkStmt.close();

            if (!exists) {
                return false; // Точки нет
            }

            // Удаляем точку
            PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);
            deleteStmt.setString(1, uuid);
            deleteStmt.setString(2, homeName);
            deleteStmt.executeUpdate();
            deleteStmt.close();

            // Лог в консоль с ником
            Bukkit.getLogger().info("Игрок " + playerName + " удалил точку '" + homeName + "'");

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public class HomeData {

    public String homeName;
    public double x, y, z;

    public HomeData(String homeName, double x, double y, double z) {
    this.homeName = homeName;
    this.x = x;
    this.y = y;
    this.z = z;
    }
    }
    public List<HomeData> getHomes(String uuid) {
        List<HomeData> homes = new ArrayList<>();
        String sql = "SELECT home_name, x, y, z FROM homes WHERE uuid = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("home_name");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                homes.add(new HomeData(name, x, y, z));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return homes;
    }
}
