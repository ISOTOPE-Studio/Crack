package cc.isotopestudio.Crack;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cc.isotopestudio.Crack.command.CrackAdminCommand;
import cc.isotopestudio.Crack.data.Data;

public class Crack extends JavaPlugin {
	public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
			.append("��������").append("]").append(ChatColor.GREEN).toString();
	public static final String pluginName = "Crack";

	public void createFile(String name) {
		File file;
		file = new File(getDataFolder(), name + ".yml");
		if (!file.exists()) {
			saveDefaultConfig();
		}
	}

	@Override
	public void onEnable() {
		Data.plugin = this;
		createFile("config");
		try {
			getRoomData().save(roomDataFile);
		} catch (IOException e) {
			getLogger().info("�����ļ�����");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.getCommand("CrackAdmin").setExecutor(new CrackAdminCommand());
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

}
