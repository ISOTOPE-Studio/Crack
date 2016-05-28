package cc.isotopestudio.Crack.data;

import cc.isotopestudio.Crack.task.TaskManager;
import cc.isotopestudio.Crack.type.LocationType;
import cc.isotopestudio.Crack.type.PlayerStatus;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static cc.isotopestudio.Crack.Crack.plugin;
import static cc.isotopestudio.Crack.data.MobSpawnObj.deserialize;

public class RoomData {
    public static HashMap<String, RoomData> rooms;
    private final String name;
    private final List<MobSpawnObj> mobSpawn;
    private final Set<String> players;
    private final HashMap<String, PlayerStatus> playersStatus;
    public Set<LivingEntity> mobs;
    public LivingEntity boss;
    public int mobsKillCount = 0;
    private Location lobby;
    private Location spawn;
    private Location respawn;
    private MobSpawnObj bossSpawn;
    private RoomStatus status;
    private int minPlayer;
    private int maxPlayer;
    private long scheduleStart = -1;

    public RoomData(String name) {
        this.name = name;
        mobSpawn = new ArrayList<>();
        players = new HashSet<>();
        mobs = new HashSet<>();
        playersStatus = new HashMap<>();
        insertInfo();
    }

    public static void update() {
        rooms = new HashMap<>();
        for (String room : plugin.getRoomData().getKeys(false)) {
            rooms.put(room, new RoomData(room));
        }
    }

    private void insertInfo() {
        lobby = getLocation("lobby");
        spawn = getLocation("spawn");
        respawn = getLocation("respawn");
        ConfigurationSection temp = plugin.getRoomData().getConfigurationSection(name + ".bossSpawn");
        if (temp != null) {
            MobSpawnObj bossTemp = deserialize(temp);
            if (bossTemp != null)
                bossSpawn = bossTemp;
        }
        ConfigurationSection mobSpawns = plugin.getRoomData().getConfigurationSection(name + ".mobspawn");
        if (mobSpawns != null)
            for (String line : mobSpawns.getKeys(false)) {
                temp = plugin.getRoomData().getConfigurationSection(name + ".mobspawn." + line);
                System.out.print(name + ".mobspawn." + line + ": " + (temp == null));
                MobSpawnObj mobSpawnObj = deserialize(temp);
                if (mobSpawnObj != null)
                    mobSpawn.add(mobSpawnObj);
            }
        minPlayer = plugin.getRoomData().getInt(name + ".min");
        maxPlayer = plugin.getRoomData().getInt(name + ".max");
        status = RoomStatus.WAITING;
        plugin.getRoomData().set(name + ".players", null);
        plugin.saveRoomData();
        System.out.println(mobSpawn);
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

    public MobSpawnObj getBossObj() {
        return bossSpawn;
    }

    public void setBossLocation(Location loc, MobData mob) {
        bossSpawn = new MobSpawnObj(loc, mob, -1);
        plugin.getRoomData().set(name + ".bossSpawn.location", Utli.locationToString(bossSpawn.getLocation()));
        plugin.getRoomData().set(name + ".bossSpawn.mob", bossSpawn.getMob().getName());
        plugin.getRoomData().set(name + ".bossSpawn.freq", bossSpawn.getFreq());
        plugin.saveRoomData();
    }

    private void saveMobSpawn() {
        plugin.getRoomData().set(name + ".mobspawn", null);
        int count = 0;
        for (MobSpawnObj mobSpawnObj : mobSpawn) {
            plugin.getRoomData().set(name + ".mobspawn." + count + ".location", Utli.locationToString(mobSpawnObj.getLocation()));
            plugin.getRoomData().set(name + ".mobspawn." + count + ".mob", mobSpawnObj.getMob().getName());
            plugin.getRoomData().set(name + ".mobspawn." + count + ".freq", mobSpawnObj.getFreq());
            count++;
        }
        plugin.saveRoomData();
    }

    public void addMobSpawn(Location loc, MobData mob, int freq) {
        MobSpawnObj mobSpawnObj = new MobSpawnObj(loc, mob, freq);
        mobSpawn.add(mobSpawnObj);
        saveMobSpawn();
    }

    public List<MobSpawnObj> getMobSpawnObj() {
        return mobSpawn;
    }

    public MobSpawnObj deleteMobSpawn(int index) {
        if (index < 0 || index >= this.mobSpawn.size())
            return null;
        saveMobSpawn();
        return this.mobSpawn.remove(index);
    }

    public void clearMobSpawn() {
        mobSpawn.clear();
        saveMobSpawn();
    }

    public boolean isFinish() {
        return lobby != null && spawn != null && respawn != null && bossSpawn != null && mobSpawn.size() > 0;
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
        playersStatus.put(playerName, PlayerStatus.GAME);
    }

    public boolean removePlayer(String playerName) {
        if (players.remove(playerName)) {
            playersStatus.remove(playerName);
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
        playersStatus.clear();
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    private void killAllMobs() {
        for (LivingEntity mob : mobs) {
            mob.setHealth(0);
        }
        mobs.clear();
        if (boss != null) {
            boss.setHealth(0);
            boss = null;
        }
    }

    public void start() {
        killAllMobs();
        mobsKillCount = 0;
        setScheduleStart(-1);
        setStatus(RoomStatus.PROGRESS);
        for (String playerName : getPlayersNames()) {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) continue;
            PlayerData.teleport(player, this, LocationType.GAME);
        }
    }

    public void boss() {
        killAllMobs();
        setStatus(RoomStatus.BOSS);
        boss = bossSpawn.getMob().spawn(bossSpawn.getLocation());
        for (String playerName : getPlayersNames()) {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) continue;
            PlayerData.teleport(player, this, LocationType.BOSS);
        }

    }

    public void win() {
        TaskManager.sendAllPlayersTitle(this, S.toGreen("Ê¤Àû£¡"), S.toAqua("»ñµÃ½±Àø"));
        end();
    }

    public void end() {
        killAllMobs();
        for (String playerName : players) {
            final Player player = Bukkit.getPlayer(playerName);
            new TeleportTask(this, player).runTaskLater(plugin, 20 * 3);
        }
        clearPlayers();
        setStatus(RoomStatus.WAITING);
    }

    public long getScheduleStart() {
        return scheduleStart;
    }

    public void setScheduleStart(long scheduleStart) {
        this.scheduleStart = scheduleStart;
    }

    public int getAlivePlayersNum() {
        int count = 0;
        for (PlayerStatus status : playersStatus.values()) {
            if (status == PlayerStatus.GAME)
                count++;
        }
        return count;
    }

    public void setPlayerStatus(String playerName, PlayerStatus status) {
        if (players.contains(playerName)) {
            playersStatus.put(playerName, status);
        }
    }

    class TeleportTask extends BukkitRunnable {

        private RoomData room;
        private Player player;

        public TeleportTask(RoomData room, Player player) {
            this.room = room;
            this.player = player;
        }

        @Override
        public void run() {
            if (player == null || !player.isOnline()) return;
            PlayerData.teleport(player, room, LocationType.NONE);
        }
    }
}
