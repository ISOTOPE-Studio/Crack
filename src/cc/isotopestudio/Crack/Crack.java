package cc.isotopestudio.Crack;

import cc.isotopestudio.Crack.command.CrackAdminCommand;
import cc.isotopestudio.Crack.command.CrackCommand;
import cc.isotopestudio.Crack.data.MobData;
import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.listener.ListenerManager;
import cc.isotopestudio.Crack.task.TaskManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Crack extends JavaPlugin {
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("��������").append("]").append(ChatColor.GREEN).toString();
    private static final String pluginName = "Crack";
    public static Crack plugin;

    @Override
    public void onEnable() {
        getLogger().info("�����ļ�...");
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
            getLogger().info("�ļ�����");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        MobData.update();
        clearPlayerData();
        ListenerManager.enable();
        TaskManager.enable();
        this.getCommand("CrackAdmin").setExecutor(new CrackAdminCommand());
        this.getCommand("Crack").setExecutor(new CrackCommand());
        RoomData.update();
        getLogger().info(pluginName + "�ɹ�����!");
        getLogger().info(pluginName + "��ISOTOPE Studio����!");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "�ɹ�ж��!");
    }

    public void onReload() {
        this.reloadConfig();
        getLogger().info(pluginName + "�ɹ�����!");
        getLogger().info(pluginName + "��ISOTOPE Studio����!");
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
            getLogger().info("�����ļ�����ʧ�ܣ�");
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
            getLogger().info("�����ļ�����ʧ�ܣ�");
        }
    }

    private void clearPlayerData() {
        for (String key : getPlayerData().getKeys(false)) {
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
