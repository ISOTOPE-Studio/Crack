package cc.isotopestudio.Crack.listener;

import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.type.RoomStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/25/2016.
 * Copyright ISOTOPE Studio
 */
class MobListener implements Listener {

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        for (RoomData room : RoomData.rooms.values()) {
            if (room.getStatus() == RoomStatus.WAITING) {
                continue;
            }
            if (event.getEntity().equals(room.boss)) {
                room.boss = null;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        room.win();
                    }
                }.runTaskLater(plugin, 20);
            }
            if (room.mobs.remove(event.getEntity())) {
                room.mobsKillCount++;
                event.setDroppedExp(0);
                event.getDrops().clear();
            }
        }
    }
}
