package cc.isotopestudio.Crack.utli;

import cc.isotopestudio.Crack.Crack;
import org.bukkit.ChatColor;

public class S {

    public static String toRed(String s) {
        return String.valueOf(ChatColor.RED) + s + ChatColor.RESET;
    }

    public static String toGreen(String s) {
        return String.valueOf(ChatColor.GREEN) + s + ChatColor.RESET;
    }

    public static String toYellow(String s) {
        return String.valueOf(ChatColor.YELLOW) + s + ChatColor.RESET;
    }

    public static String toAqua(String s) {
        return String.valueOf(ChatColor.AQUA) + s + ChatColor.RESET;
    }

    public static String toGray(String s) {
        return String.valueOf(ChatColor.GRAY) + s + ChatColor.RESET;
    }

    public static String toGold(String s) {
        return String.valueOf(ChatColor.GOLD) + s + ChatColor.RESET;
    }

    public static String toBoldGreen(String s) {
        return String.valueOf(ChatColor.GREEN) + ChatColor.BOLD + s + ChatColor.RESET;
    }

    public static String toBoldDarkGreen(String s) {
        return String.valueOf(ChatColor.DARK_GREEN) + ChatColor.BOLD + s + ChatColor.RESET;
    }

    public static String toBoldDarkAqua(String s) {
        return String.valueOf(ChatColor.DARK_AQUA) + ChatColor.BOLD + s + ChatColor.RESET;
    }

    public static String toBoldPurple(String s) {
        return String.valueOf(ChatColor.LIGHT_PURPLE) + ChatColor.BOLD + s + ChatColor.RESET;
    }

    public static String toBoldGold(String s) {
        return String.valueOf(ChatColor.GOLD) + ChatColor.BOLD + s + ChatColor.RESET;
    }

    public static String toPrefixRed(String s) {
        return Crack.prefix + ChatColor.RED + s + ChatColor.RESET;
    }

    public static String toPrefixGreen(String s) {
        return Crack.prefix + ChatColor.GREEN + s + ChatColor.RESET;
    }

    public static String toPrefixYellow(String s) {
        return Crack.prefix + ChatColor.YELLOW + s + ChatColor.RESET;
    }

    public static String toPrefixGray(String s) {
        return Crack.prefix + ChatColor.GRAY + s + ChatColor.RESET;
    }

    public static String toBoldRed(String s) {
        return String.valueOf(ChatColor.RED) + ChatColor.BOLD + s + ChatColor.RESET;
    }

    public static String toPrefixAqua(String s) {
        return Crack.prefix + ChatColor.AQUA + s + ChatColor.RESET;
    }
}
