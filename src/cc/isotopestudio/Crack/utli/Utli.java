package cc.isotopestudio.Crack.utli;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Mars on 5/15/2016.
 * Copyright ISOTOPE Studio
 */
public class Utli {

    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }

    public static Location stringToLocation(String string) {
        if (string == null) return null;
        String[] s = string.split(" ");
        if (s.length != 4) return null;
        World world = Bukkit.getWorld(s[0]);
        int x = Integer.parseInt(s[1]);
        int y = Integer.parseInt(s[2]);
        int z = Integer.parseInt(s[3]);
        return new Location(world, x, y, z);
    }

    public static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min) + min + 1);
    }
}
