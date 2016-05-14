package cc.isotopestudio.Crack.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

import static cc.isotopestudio.Crack.Crack.plugin;

public class RoomData {
    private final String name;
    private Location lobby;
    private Location spawn;
    private Location respawn;
    private List<Location> mobSpawn;

    public RoomData(String name) {
        this.name = name;
        mobSpawn = new ArrayList<>();
        insertLocation();
    }

    private void insertLocation() {
        lobby = getLocation("lobby");
        spawn = getLocation("spawn");
        respawn = getLocation("respawn");
        mobSpawn.clear();
        for (String line : plugin.getRoomData().getStringList(name + ".mobspawn")) {
            String[] s = line.split(" ");
            World world = Bukkit.getWorld(s[0]);
            int x = Integer.parseInt(s[1]);
            int y = Integer.parseInt(s[2]);
            int z = Integer.parseInt(s[3]);
            mobSpawn.add(new Location(world, x, y, z));
        }
    }

    private Location getLocation(String key) {
        String line = plugin.getRoomData().getString(name + "." + key, null);
        if (line == null)
            return null;
        String[] s = line.split(" ");
        World world = Bukkit.getWorld(s[0]);
        int x = Integer.parseInt(s[1]);
        int y = Integer.parseInt(s[2]);
        int z = Integer.parseInt(s[3]);
        return new Location(world, x, y, z);
    }

    private String locationToString(Location loc) {
        return loc.getWorld().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }

    private void storeLocation(String key, Location loc) {
        plugin.getRoomData().set(name + "." + key, locationToString(loc));
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

    public boolean isExist() {
        if (lobby == null && spawn == null && respawn == null && mobSpawn.size() == 0)
            return false;
        return true;
    }

    public boolean isFinish() {
        return true;
    }

    public void addMobSpawn(Location loc) {
        mobSpawn.add(loc);
        List<String> list = new ArrayList<>();
        for (Location location : mobSpawn) {
            list.add(locationToString(location));
        }
        plugin.getRoomData().set(name + "." + "mobspawn", list);
        plugin.saveRoomData();
    }

    public List<Location> getMobSpawn() {
        return mobSpawn;
    }

    public Location deleteMobSpawn(int index) {
        if (index < 0 || index >= this.mobSpawn.size())
            return null;
        return this.mobSpawn.remove(index);
    }
}
