package cc.isotopestudio.Crack;

import java.io.File;
import java.io.IOException;

import cc.isotopestudio.Crack.command.CrackCommand;
import cc.isotopestudio.Crack.data.RoomData;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cc.isotopestudio.Crack.command.CrackAdminCommand;

public class Crack extends JavaPlugin {
	public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
			.append("副本房间").append("]").append(ChatColor.GREEN).toString();
	private static final String pluginName = "Crack";
	public static Crack plugin;

	private void createFile(String name) {
		File file;
		file = new File(getDataFolder(), name + ".yml");
		if (!file.exists()) {
			saveDefaultConfig();
		}
	}

	@Override
	public void onEnable() {
        plugin = this;
		createFile("config");
		try {
			getRoomData().save(roomDataFile);
            getPlayerData().save(playerDataFile);
		} catch (IOException e) {
			getLogger().info("副本文件出错！");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.getCommand("CrackAdmin").setExecutor(new CrackAdminCommand());
		this.getCommand("Crack").setExecutor(new CrackCommand());
        RoomData.update();
		getLogger().info(pluginName + "成功加载!");
		getLogger().info(pluginName + "由ISOTOPE Studio制作!");
		getLogger().info("http://isotopestudio.cc");
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

}
