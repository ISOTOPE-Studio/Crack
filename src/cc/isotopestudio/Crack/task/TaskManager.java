package cc.isotopestudio.Crack.task;

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

        MobSpawnTask mobSpawnTask = new MobSpawnTask();
        mobSpawnTask.runTaskTimer(plugin, 20, 20);
        tasks.add(mobSpawnTask);
    }
}