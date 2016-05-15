package cc.isotopestudio.Crack.data;

import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cc.isotopestudio.Crack.Crack.plugin;

public class RoomData {
    private final String name;
    private Location lobby;
    private Location spawn;
    private Location respawn;
    private List<Location> mobSpawn;

    public static HashMap<String, RoomData> rooms;

    public static void update() {
        rooms = new HashMap<>();
        for (String room : plugin.getRoomData().getKeys(false)) {
            rooms.put(room, new RoomData(room));
        }
    }

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
            mobSpawn.add(Utli.stringToLocation(line));
        }
    }

    private Location getLocation(String key) {
        return Utli.stringToLocation(plugin.getRoomData().getString(name + "." + key, null));
    }

    private void storeLocation(String key, Location loc) {
        plugin.getRoomData().set(name + "." + key, Utli.locationToString(loc));
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
        if (lobby != null && spawn != null && respawn != null && mobSpawn.size() > 0)
            return true;
        return false;
    }

    public void addMobSpawn(Location loc) {
        mobSpawn.add(loc);
        List<String> list = new ArrayList<>();
        for (Location location : mobSpawn) {
            list.add(Utli.locationToString(location));
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
