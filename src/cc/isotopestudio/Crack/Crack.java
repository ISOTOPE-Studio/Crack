package cc.isotopestudio.Crack;

import cc.isotopestudio.Crack.Room.Room;
import cc.isotopestudio.Crack.command.CrackAdminCommand;
import cc.isotopestudio.Crack.command.CrackCommand;
import cc.isotopestudio.Crack.Mob.Mob;
import cc.isotopestudio.Crack.data.Settings;
import cc.isotopestudio.Crack.listener.ListenerManager;
import cc.isotopestudio.Crack.task.TaskManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class Crack extends JavaPlugin {
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("副本房间").append("]").append(ChatColor.GREEN).toString();
    private static final String pluginName = "Crack";
    public static Crack plugin;

    @Override
    public void onEnable() {
        getLogger().info("加载文件...");
        plugin = this;
        File file;
        file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        try {
            getRoomData().save(roomDataFile);
            getPlayerData().save(playerDataFile);
            getMobsData().save(mobsDataFile);
        } catch (IOException e) {
            getLogger().info("文件出错！");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Settings.update();
        Mob.update();
        clearPlayerData();
        ListenerManager.enable();
        TaskManager.enable();
        this.getCommand("CrackAdmin").setExecutor(new CrackAdminCommand());
        this.getCommand("Crack").setExecutor(new CrackCommand());
        Room.update();
        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");

        new InfoGUI().run();
        InfoGUI.text.setWrapStyleWord(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                InfoGUI.text.setText("");
                for (Room room : Room.rooms.values())
                    InfoGUI.text.append(room.toString() + "\n");
            }
        }.runTaskTimer(this, 20, 20);

    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "成功卸载!");
    }

    public void onReload() {
        this.reloadConfig();
        getLogger().info(pluginName + "成功重载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
    }

    private File roomDataFile = null;
    private FileConfiguration roomData = null;

    public void reloadRoomData() {
        if (roomDataFile == null) {
            roomDataFile = new File(getDataFolder(), "rooms.yml");
        }
        roomData = YamlConfiguration.loadConfiguration(roomDataFile);
    }

    public FileConfiguration getRoomData() {
        if (roomData == null) {
            reloadRoomData();
        }
        return roomData;
    }

    public void saveRoomData() {
        if (roomData == null || roomDataFile == null) {
            return;
        }
        try {
            getRoomData().save(roomDataFile);
        } catch (IOException ex) {
            getLogger().info("副本文件保存失败！");
        }
    }

    private File playerDataFile = null;
    private FileConfiguration playerData = null;

    public void reloadPlayerData() {
        if (playerDataFile == null) {
            playerDataFile = new File(getDataFolder(), "players.yml");
        }
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public FileConfiguration getPlayerData() {
        if (playerData == null) {
            reloadPlayerData();
        }
        return playerData;
    }

    public void savePlayerData() {
        if (playerData == null || playerDataFile == null) {
            return;
        }
        try {
            getPlayerData().save(playerDataFile);
        } catch (IOException ex) {
            getLogger().info("副本文件保存失败！");
        }
    }

    private void clearPlayerData() {
        for (String key : getPlayerData().getKeys(false)) {
            if (!key.equals("date"))
                getPlayerData().set(key, null);
        }
        savePlayerData();
    }

    private File mobsDataFile = null;
    private FileConfiguration mobsData = null;

    public void reloadMobsData() {
        if (mobsDataFile == null) {
            this.saveResource("mobs.yml", false);
        }
        mobsDataFile = new File(getDataFolder(), "mobs.yml");
        mobsData = YamlConfiguration.loadConfiguration(mobsDataFile);
    }

    public FileConfiguration getMobsData() {
        if (mobsData == null) {
            reloadMobsData();
        }
        return mobsData;
    }

}
