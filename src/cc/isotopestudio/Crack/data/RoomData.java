package cc.isotopestudio.Crack.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import static cc.isotopestudio.Crack.data.Data.plugin;

public class RoomData {
	private final String name;
	private Location lobby;
	private Location spawn;
	private Location respawn;

	public RoomData(String name, boolean b) {
		this.name = name;
		System.out.print(name);
		if (b) {
			getLocation();
		}
	}

	public void getLocation() {
		lobby = getLocation("lobby");
		spawn = getLocation("spawn");
		respawn = getLocation("respawn");
	}

	private Location getLocation(String key) {
		String w = plugin.getRoomData().getString(name + "." + key + ".world", null);
		if (w == null)
			return null;
		int x = plugin.getRoomData().getInt(name + "." + key + ".x");
		int y = plugin.getRoomData().getInt(name + "." + key + ".y");
		int z = plugin.getRoomData().getInt(name + "." + key + ".z");
		World world = Bukkit.getWorld(w);
		return new Location(world, x, y, z);
	}

	private void storeLocation(String key, Location loc) {
		plugin.getRoomData().set(name + "." + key + ".x", loc.getBlockX());
		plugin.getRoomData().set(name + "." + key + ".y", loc.getBlockY());
		plugin.getRoomData().set(name + "." + key + ".z", loc.getBlockZ());
		plugin.getRoomData().set(name + "." + key + ".world", loc.getWorld().getName());
		plugin.saveRoomData();
	}

	public String getName() {
		return name;
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location loc) {
		lobby = loc;
		storeLocation("lobby", loc);
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location loc) {
		this.spawn = loc;
		storeLocation("spawn", loc);
	}

	public Location getRespawn() {
		return respawn;
	}

	public void setRespawn(Location loc) {
		this.respawn = loc;
		storeLocation("respawn", loc);
	}

}
