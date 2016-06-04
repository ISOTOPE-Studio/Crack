package cc.isotopestudio.Crack.listener;

import cc.isotopestudio.Crack.data.PlayerData;
import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.type.LocationType;
import cc.isotopestudio.Crack.type.PlayerStatus;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/21/2016.
 * Copyright ISOTOPE Studio
 */
class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String name = event.getPlayer().getName();
        PlayerData.resetPlayer(name);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        PlayerData.resetPlayer(name);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Room room = PlayerData.getRoom(player.getName());
        if (room == null) return;
        room.setPlayerStatus(player.getName(), PlayerStatus.DEATH);
        player.sendMessage(S.toPrefixRed("你死了!"));
        if (room.getAlivePlayersNum() == 0)
            room.end();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Room room = PlayerData.getRoom(player.getName());
        if (room == null) return;
        PlayerData.teleport(player, room, LocationType.RESPAWN);
        player.sendTitle(S.toYellow("复活中..."), S.toAqua("还有 30 秒"));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    return;
                }
                if (room.getStatus() != RoomStatus.WAITING) {
                    PlayerData.teleport(player, room, LocationType.GAME);
                    room.setPlayerStatus(player.getName(), PlayerStatus.GAME);
                    player.sendMessage(S.toPrefixYellow("传送到房间"));
                }
            }
        }.runTaskLater(plugin, 30 * 20);
    }

}
