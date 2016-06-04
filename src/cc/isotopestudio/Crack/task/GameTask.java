package cc.isotopestudio.Crack.task;

import cc.isotopestudio.Crack.room.MobSpawnObj;
import cc.isotopestudio.Crack.room.Room;
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

    private HashMap<Room, Integer> count = new HashMap<>();

    @Override
    public void run() {
        for (Room room : Room.rooms.values()) {
            if (!count.containsKey(room))
                count.put(room, 0);
            else
                count.put(room, count.get(room) + 1);
            if (room.getStatus() != RoomStatus.PROGRESS)
                continue;
            if (room.getAlivePlayersNum() == 0) {

                room.end();
                continue;
            }
            if (room.mobsKillCount > 20) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        room.boss();
                    }
                }.runTaskLater(plugin, 20);
                continue;
            }
            for (MobSpawnObj mobSpawnObj : room.getMobSpawnObj()) {
                if (count.get(room) % mobSpawnObj.getFreq() == 0)
                    if (mobSpawnObj.isAvailble())
                        room.mobs.add(mobSpawnObj.spawn());
            }
        }
    }

}
