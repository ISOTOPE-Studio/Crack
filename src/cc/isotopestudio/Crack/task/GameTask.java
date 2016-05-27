package cc.isotopestudio.Crack.task;

import cc.isotopestudio.Crack.data.MobSpawnObj;
import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.data.Settings;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static cc.isotopestudio.Crack.task.TaskManager.sendAllPlayers;

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
                sendAllPlayers(room, S.toPrefixYellow("”Œœ∑Ω· ¯ :-("));
                room.end();
                return;
            }
            for (MobSpawnObj mobSpawnObj : room.getMobSpawnObj()) {
                if (count.get(room) %
                        (mobSpawnObj.getFreq() < 0 ? Settings.mobSpawnFreq : mobSpawnObj.getFreq()) == 0)
                    room.mobs.add(spawnMob(mobSpawnObj));
            }
        }
    }

    private LivingEntity spawnMob(MobSpawnObj mobSpawnObj) {
        return (LivingEntity) mobSpawnObj.getLocation().getWorld().spawn(mobSpawnObj.getLocation(), mobSpawnObj.getMob().getType().getEntityClass());
    }
}
