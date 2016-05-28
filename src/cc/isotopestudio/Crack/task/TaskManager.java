package cc.isotopestudio.Crack.task;

import cc.isotopestudio.Crack.data.RoomData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/21/2016.
 * Copyright ISOTOPE Studio
 */
public class TaskManager {
    private static ArrayList<BukkitRunnable> tasks = new ArrayList<>();

    public static void enable() {
        for (BukkitRunnable task : tasks) {
            task.cancel();
            tasks.remove(task);
        }
        LobbyTask lobbyTask = new LobbyTask();
        lobbyTask.runTaskTimer(plugin, 20, 20);
        tasks.add(lobbyTask);

        GameTask gameTask = new GameTask();
        gameTask.runTaskTimer(plugin, 20, 20);
        tasks.add(gameTask);
    }

    public static void sendAllPlayers(RoomData room, String msg) {
        for (String playerName : room.getPlayersNames()) {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) continue;
            player.sendMessage(msg);
        }
    }
}
