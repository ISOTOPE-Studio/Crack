package cc.isotopestudio.Crack.listener;

import cc.isotopestudio.Crack.room.MobSpawnObj;
import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.type.RoomStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 * Created by Mars on 5/25/2016.
 * Copyright ISOTOPE Studio
 */
class MobListener implements Listener {

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        for (Room room : Room.rooms.values()) {
            if (room.getStatus() == RoomStatus.WAITING) {
                continue;
            }
            if (room.getBossObj().getMobs().size() > 0 &&
                    room.getBossObj().getMobs().contains(event.getEntity())) {
                room.getBossObj().clear();
                room.win();
            }

            for (MobSpawnObj mobSpawnObj : room.getMobSpawnObj())
                if (mobSpawnObj.remove(event.getEntity())) {
                    room.mobsKillCount++;
                    event.setDroppedExp(0);
                    event.getDrops().clear();
                }
        }
    }

    @EventHandler
    public void onMobTargeting(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) return;
        event.setCancelled(true);
    }
}
