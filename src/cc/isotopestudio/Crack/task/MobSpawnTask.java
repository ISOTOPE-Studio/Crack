package cc.isotopestudio.Crack.task;

import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.type.RoomStatus;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Mars on 5/21/2016.
 * Copyright ISOTOPE Studio
 */
class MobSpawnTask extends BukkitRunnable {

    @Override
    public void run() {
        for (RoomData room : RoomData.rooms.values()) {
            if (room.getStatus() != RoomStatus.PROGRESS)
                continue;

        }
    }
}