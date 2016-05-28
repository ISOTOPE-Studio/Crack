package cc.isotopestudio.Crack.task;

import cc.isotopestudio.Crack.data.MobSpawnObj;
import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.data.Settings;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static cc.isotopestudio.Crack.Crack.plugin;
import static cc.isotopestudio.Crack.task.TaskManager.sendAllPlayersTitle;

/**
 * Created by Mars on 5/25/2016.
 * Copyright ISOTOPE Studio
 */
class GameTask extends BukkitRunnable {

    private HashMap<RoomData, Integer> count = new HashMap<>();

    @Override
    public void run() {
        for (RoomData room : RoomData.rooms.values()) {
            if (!count.containsKey(room))
                count.put(room, 0);
            else
                count.put(room, count.get(room) + 1);
            if (room.getStatus() != RoomStatus.PROGRESS)
                continue;
            if (room.getAlivePlayersNum() == 0) {
                sendAllPlayersTitle(room, S.toBoldRed("无人幸存"), S.toYellow("游戏结束 :-("));
                room.end();
                continue;
            }
            if (room.mobsKillCount > 20) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        sendAllPlayersTitle(room, S.toBoldRed("红色警报"), S.toRed("BOSS来临!"));
                        room.boss();
                    }
                }.runTaskLater(plugin, 20);
                continue;
            }
            for (MobSpawnObj mobSpawnObj : room.getMobSpawnObj()) {
                if (count.get(room) %
                        (mobSpawnObj.getFreq() < 0 ? Settings.mobSpawnFreq : mobSpawnObj.getFreq()) == 0)
                    room.mobs.add(mobSpawnObj.getMob().spawn(mobSpawnObj.getLocation()));
            }
        }
    }

}
