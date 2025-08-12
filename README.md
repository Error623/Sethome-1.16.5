# Sethome-1.16.5

## English
A plugin for Minecraft servers (Spigot API 1.16.5) that allows players to set and teleport to saved home points.  
Uses **SQLite** for data storage.

### Commands
- `/sethome <home_name>` — set a home point.
- `/delhome <home_name>` — delete a home point.
- `/homes` — open a GUI menu with saved homes.

### Features
- Unlimited home points per player.
- Database file is created automatically after server start in the plugin folder.

### Requirements
- Minecraft server **1.16+** (Bukkit or Spigot).
- Java 8 or higher.

### Installation
1. Download the `.jar` file of the plugin.
2. Place it into your server's `plugins` folder.
3. Start or reload your server.
4. The database file `homes.db` will be created automatically.

### Configuration
No configuration is required. All data is saved automatically in  
`plugins/SetHome/homes.db`.

---

## Русский
Плагин для Minecraft серверов (Spigot API 1.16.5), позволяющий игрокам устанавливать и телепортироваться в сохранённые точки домов.  
Использует **SQLite** для хранения данных.

### Команды
- `/sethome <название_дома>` — установить точку дома.
- `/delhome <название_дома>` — удалить точку дома.
- `/homes` — открыть GUI-меню с сохранёнными домами.

### Возможности
- Неограниченное количество точек дома на игрока.
- Файл базы данных создаётся автоматически после запуска сервера в папке плагина.

### Требования
- Minecraft сервер **1.16+** (Bukkit или Spigot).
- Java 8 или выше.

### Установка
1. Скачайте `.jar` файл плагина.
2. Переместите его в папку `plugins` вашего сервера.
3. Запустите или перезапустите сервер.
4. Файл базы данных `homes.db` будет создан автоматически.

### Конфигурация
Конфигурация не требуется. Все данные сохраняются автоматически в  
`plugins/SetHome/homes.db`.

---

## License
This project is licensed under the **MIT License** — you are free to use, modify, and distribute it.
