package cc.isotopestudio.Crack.utli;

import cc.isotopestudio.Crack.room.Room;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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

    public static int random(int min, int max) {
        double ran = Math.random() * (max - min + 1) + min;
        return (int) ran;
    }

    public static boolean random(int count) {
        int ran = (int) (Math.random() * 30) + 1;
        return count == ran;
    }

    public static void sendAllPlayers(Room room, String msg) {
        for (String playerName : room.getPlayersNames()) {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) continue;
            player.sendMessage(msg);
        }
    }

    @SuppressWarnings("deprecation")
    public static void sendAllPlayersTitle(Room room, String title, String subtitle) {
        for (String playerName : room.getPlayersNames()) {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) continue;
            player.sendTitle(title, subtitle);
        }
    }
}
