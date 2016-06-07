package cc.isotopestudio.Crack.task;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/21/2016.
 * Copyright ISOTOPE Studio
 */
public class TaskManager {
    private static final ArrayList<BukkitRunnable> tasks = new ArrayList<>();

    public static void enable() {
        for (BukkitRunnable task : tasks) {
            task.cancel();
            tasks.remove(task);
        }
        LobbyTask lobbyTask = new LobbyTask();
        lobbyTask.runTaskTimer(plugin, 20, 20);
        tasks.add(lobbyTask);

        GameTask gameTask = new GameTask();
        gameTask.runTaskTimer(plugin, 30, 20);
        tasks.add(gameTask);

        DateTask dateTask = new DateTask();
        dateTask.runTaskTimer(plugin, 40, 20 * 60 * 60);
        tasks.add(dateTask);
    }

}
