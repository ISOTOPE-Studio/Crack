package cc.isotopestudio.Crack.task;

import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

import static cc.isotopestudio.Crack.task.TaskManager.sendAllPlayers;
import static cc.isotopestudio.Crack.task.TaskManager.sendAllPlayersTitle;

/**
 * Created by Mars on 5/21/2016.
 * Copyright ISOTOPE Studio
 */
class LobbyTask extends BukkitRunnable {
    private int waitCount = 0;

    private static final int waitInterval = 7;
    private static final int startWaitInterval = 30;
    private static final int[] startWaitAnnounce = {60, 30, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    @Override
    public void run() {
        waitCount++;
        if (waitCount == waitInterval) waitCount = 0;
        for (Room room : Room.rooms.values()) {
            if (room.getStatus() != RoomStatus.WAITING)
                continue;

            if (room.getPlayerNum() >= room.getMinPlayer()) {
                if (room.getScheduleStart() < 0) {
                    room.setScheduleStart(new Date().getTime() + startWaitInterval * 1000);
                    sendAllPlayers(room, S.toPrefixYellow("还有 " + startWaitInterval + " 秒开始游戏"));
                } else {
                    int sec = getRemainSec(room.getScheduleStart());
                    for (int num : startWaitAnnounce) {
                        if (num == sec) {
                            sendAllPlayers(room, S.toPrefixYellow("还有 " + sec + " 秒开始游戏"));
                        }
                    }
                }
            } else {
                room.setScheduleStart(-1);
            }
            if (getRemainSec(room.getScheduleStart()) == 0) {
                sendAllPlayersTitle(room, S.toGreen("开始游戏!"), S.toBoldPurple("怪物来袭"));
                room.start();
            }
            if (waitCount == 0)
                sendAllPlayers(room, S.toPrefixYellow("你在大厅里，等待其他玩家进入   ") + S.toGreen(
                        room.getPlayerNum() + " / " + room.getMinPlayer() + " / " + room.getMaxPlayer()));
        }
    }

    private int getRemainSec(long time) {
        long now = new Date().getTime();
        return (int) ((time - now) / 1000);
    }
}
