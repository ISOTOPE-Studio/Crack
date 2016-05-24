package cc.isotopestudio.Crack.data;

import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Location;

import java.util.*;

import static cc.isotopestudio.Crack.Crack.plugin;

public class RoomData {
    private final String name;
    private Location lobby;
    private Location spawn;
    private Location respawn;
    private List<Location> mobSpawn;
    private RoomStatus status;
    private int minPlayer;
    private int maxPlayer;
    private Set<String> players;
    private long scheduleStart = -1;

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
        players = new HashSet<>();
        insertInfo();
    }

    private void insertInfo() {
        lobby = getLocation("lobby");
        spawn = getLocation("spawn");
        respawn = getLocation("respawn");
        mobSpawn.clear();
        for (String line : plugin.getRoomData().getStringList(name + ".mobspawn")) {
            mobSpawn.add(Utli.stringToLocation(line));
        }
        minPlayer = plugin.getRoomData().getInt(name + ".min");
        maxPlayer = plugin.getRoomData().getInt(name + ".max");
        status = RoomStatus.valueOf(plugin.getRoomData().getString(name + ".status", RoomStatus.WAITING.name()));
        plugin.getRoomData().set(name + ".players", null);
        plugin.saveRoomData();
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

    public boolean isFinish() {
        if (lobby != null && spawn != null && respawn != null && mobSpawn.size() > 0)
            return true;
        return false;
    }

    private void saveMobSpawn() {
        List<String> list = new ArrayList<>();
        for (Location location : mobSpawn) {
            list.add(Utli.locationToString(location));
        }
        plugin.getRoomData().set(name + ".mobspawn", list);
        plugin.saveRoomData();
    }

    public void addMobSpawn(Location loc) {
        mobSpawn.add(loc);
        saveMobSpawn();
    }

    public List<Location> getMobSpawn() {
        return mobSpawn;
    }

    public Location deleteMobSpawn(int index) {
        if (index < 0 || index >= this.mobSpawn.size())
            return null;
        saveMobSpawn();
        return this.mobSpawn.remove(index);
    }

    public void clearMobSpawn() {
        mobSpawn.clear();
        saveMobSpawn();
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
        plugin.getRoomData().set(name + ".min", minPlayer);
        plugin.saveRoomData();
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
        plugin.getRoomData().set(name + ".max", maxPlayer);
        plugin.saveRoomData();
    }

    public void addPlayer(String playerName) {
        players.add(playerName);
        savePlayers();
    }

    public boolean removePlayer(String playerName) {
        if (players.remove(playerName)) {
            savePlayers();
            return true;
        }
        return false;
    }

    public int getPlayerNum() {
        return players.size();
    }

    public Set<String> getPlayersNames() {
        return players;
    }

    public void clearPlayers() {
        players.clear();
        savePlayers();
    }

    private void savePlayers() {
        List<String> list = new ArrayList<>();
        for (String player : players) {
            list.add(player);
        }
        plugin.getRoomData().set(this.getName() + ".players", list);
        plugin.saveRoomData();
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public boolean start() {
        return true;
    }

}
