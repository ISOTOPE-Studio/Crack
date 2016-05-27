package cc.isotopestudio.Crack.data;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class Settings {
    public static int mobSpawnFreq = 3;

    public static void update() {
        mobSpawnFreq = plugin.getConfig().getInt("mob.spawnfreq");
    }
}
