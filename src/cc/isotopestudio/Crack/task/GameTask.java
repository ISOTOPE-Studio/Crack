package cc.isotopestudio.Crack.task;

import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import static cc.isotopestudio.Crack.task.TaskManager.sendAllPlayers;

/**
 * Created by Mars on 5/25/2016.
 * Copyright ISOTOPE Studio
 */
class GameTask extends BukkitRunnable {

    @Override
    public void run() {
        for (RoomData room : RoomData.rooms.values()) {
            if (room.getStatus() != RoomStatus.PROGRESS)
                continue;
            if (room.getAlivePlayersNum() == 0) {
                sendAllPlayers(room, S.toPrefixYellow("”Œœ∑Ω· ¯ :-("));
                room.end();
                return;
            }
            for (Location loc : room.getMobSpawn()) {
                room.mobs.add(spawnMob(loc));
            }
        }
    }

    private LivingEntity spawnMob(Location loc) {
        switch (Utli.getRandom(0, 4)) {
            case (0): {
                return loc.getWorld().spawn(loc, Zombie.class);
            }
            case (1): {
                return loc.getWorld().spawn(loc, Skeleton.class);
            }
            case (2): {
                return loc.getWorld().spawn(loc, PigZombie.class);
            }
            case (3): {
                return loc.getWorld().spawn(loc, Slime.class);
            }
            case (4): {
                return loc.getWorld().spawn(loc, Creeper.class);
            }
            default: {
                return loc.getWorld().spawn(loc, Sheep.class);
            }
        }
    }
}
