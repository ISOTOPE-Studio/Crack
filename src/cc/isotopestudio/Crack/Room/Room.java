package cc.isotopestudio.Crack.Room;

import cc.isotopestudio.Crack.Mob.Mob;
import cc.isotopestudio.Crack.data.PlayerData;
import cc.isotopestudio.Crack.task.TaskManager;
import cc.isotopestudio.Crack.type.LocationType;
import cc.isotopestudio.Crack.type.PlayerStatus;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static cc.isotopestudio.Crack.Crack.plugin;
import static cc.isotopestudio.Crack.Room.MobSpawnObj.deserialize;
import static cc.isotopestudio.Crack.task.TaskManager.sendAllPlayersTitle;

public class Room {
    public static HashMap<String, Room> rooms;
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

    private List<String> rewards;
    private ArrayList<Integer> rewardPro;
    private int rewardProSum;

    public Room(String name) {
        this.name = name;
        mobSpawn = new ArrayList<>();
        players = new HashSet<>();
        mobs = new HashSet<>();
        rewards = new ArrayList<>();
        playersStatus = new HashMap<>();
        insertInfo();
    }

    public static void update() {
        rooms = new HashMap<>();
        for (String room : plugin.getRoomData().getKeys(false)) {
            rooms.put(room, new Room(room));
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
                MobSpawnObj mobSpawnObj = deserialize(temp);
                if (mobSpawnObj != null)
                    mobSpawn.add(mobSpawnObj);
            }
        minPlayer = plugin.getRoomData().getInt(name + ".min");
        maxPlayer = plugin.getRoomData().getInt(name + ".max");
        status = RoomStatus.WAITING;
        boolean hasReward = false;
        rewards.clear();
        rewards.addAll(plugin.getRoomData().getStringList(name + ".reward"));
        if (rewards.size() == 0) {
            rewards.add("give %player% diamond 1;give %player% stone 64;5");
            plugin.getRoomData().set(name + ".reward", rewards);
        }
        rewardPro = new ArrayList<Integer>();
        rewardProSum = 0;
        for (int i = 0; i < rewards.size(); i++) {
            String reward = rewards.get(i);
            String[] s = reward.split(";");
            int pro = Integer.parseInt(s[s.length - 1]);
            if (i == 0)
                rewardPro.add(pro);
            else
                rewardPro.add(pro + rewardPro.get(i - 1));
            rewardProSum += pro;
        }
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

    public void setBossLocation(Location loc, Mob mob) {
        bossSpawn = new MobSpawnObj(loc, mob, -1, -1);
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
            plugin.getRoomData().set(name + ".mobspawn." + count + ".limit", mobSpawnObj.getLimit());
            count++;
        }
        plugin.saveRoomData();
    }

    public void addMobSpawn(Location loc, Mob mob, int freq, int limit) {
        MobSpawnObj mobSpawnObj = new MobSpawnObj(loc, mob, freq, limit);
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
        for (MobSpawnObj mobSpawnObj : mobSpawn) {
            mobSpawnObj.resetCount();
        }
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

        new BukkitRunnable() {
            @Override
            public void run() {
                if (status == RoomStatus.BOSS)
                    boss = bossSpawn.getMob().spawn(bossSpawn.getLocation());
            }
        }.runTaskLater(plugin, 20 * 5);

        for (String playerName : getPlayersNames()) {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) continue;
            sendAllPlayersTitle(this, S.toBoldRed("红色警报"), S.toRed("还有5秒 BOSS来临!"));
            PlayerData.teleport(player, this, LocationType.BOSS);
        }

    }

    public void win() {
        TaskManager.sendAllPlayersTitle(this, S.toGreen("胜利！"), S.toAqua("获得奖励"));
        for (String playerName : players) {
            final Player player = Bukkit.getPlayer(playerName);
            if (player == null) continue;
            int n = Utli.random(0, rewardProSum);
            int index = 0;
            while (true) {
                if (rewardPro.get(index) < n) {
                    index++;
                    continue;
                }
                break;
            }
            String line = rewards.get(index);
            line = line.replace("%player%", player.getName());
            String commands[] = line.split(";");
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            for (int i = 0; i < commands.length - 1; i++) {
                Bukkit.dispatchCommand(console, commands[i]);
            }
        }
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

    @Override
    public String toString() {
        return "Room{" + "name='" + name + '\'' +
                "\nmobSpawn=" + mobSpawn +
                "\nplayers=" + players +
                "\nplayersStatus=" + playersStatus +
                "\nmobs=" + mobs +
                "\nboss=" + boss +
                "\nmobsKillCount=" + mobsKillCount +
                "\nlobby=" + (lobby != null) +
                "\nspawn=" + (spawn != null) +
                "\nrespawn=" + (respawn != null) +
                "\nbossSpawn=" + (bossSpawn != null) +
                "\nstatus=" + status +
                "\nminPlayer=" + minPlayer +
                "\nmaxPlayer=" + maxPlayer +
                "\nscheduleStart=" + scheduleStart +
                "\nrewards=" + rewards +
                "\nrewardPro=" + rewardPro +
                '}';
    }

    private class TeleportTask extends BukkitRunnable {

        private Room room;
        private Player player;

        TeleportTask(Room room, Player player) {
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
