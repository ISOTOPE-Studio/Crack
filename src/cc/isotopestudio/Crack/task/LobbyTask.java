package cc.isotopestudio.Crack.task;

import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Mars on 5/21/2016.
 * Copyright ISOTOPE Studio
 */
class LobbyTask extends BukkitRunnable {
    private int waitCount = 0;

    @Override
    public void run() {
        waitCount++;
        if (waitCount == 5) waitCount = 0;
        for (RoomData room : RoomData.rooms.values()) {
            if (room.getStatus() != RoomStatus.WAITING)
                continue;
            if (waitCount == 0)
                for (String playerName : room.getPlayersNames()) {
                    Player player = Bukkit.getPlayer(playerName);
                    if (player == null) continue;
                    player.sendMessage(S.toPrefixYellow("你在大厅里，等待其他玩家进入   ") + S.toGreen(
                            room.getPlayerNum() + " / " + room.getMinPlayer() + " / " + room.getMaxPlayer()));
                }
        }
    }
}
