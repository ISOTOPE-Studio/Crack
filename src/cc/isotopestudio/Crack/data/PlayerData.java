package cc.isotopestudio.Crack.data;

import cc.isotopestudio.Crack.type.LocationType;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.entity.Player;

import static cc.isotopestudio.Crack.Crack.plugin;
import static cc.isotopestudio.Crack.type.LocationType.NONE;

/**
 * Created by Mars on 5/15/2016.
 * Copyright ISOTOPE Studio
 */
public class PlayerData {

    public static void resetPlayer(String playerName) {
        RoomData room = getRoom(playerName);
        if (room != null)
            room.removePlayer(playerName);
        plugin.getPlayerData().set(playerName, null);
        plugin.savePlayerData();
    }

    public static LocationType getLocationType(String playerName) {
        if (plugin.getPlayerData().getString(playerName + ".type") == null)
            return NONE;
        return LocationType.valueOf(plugin.getPlayerData().getString(playerName + ".type"));
    }

    public static boolean teleport(Player player, RoomData room, LocationType type) {
        if (!room.isFinish()) return false;
        switch (type) {
            case LOBBY: {
                plugin.getPlayerData().set(player.getName() + ".location", Utli.locationToString(player.getLocation()));
                plugin.getPlayerData().set(player.getName() + ".type", type.name());
                plugin.getPlayerData().set(player.getName() + ".room", room.getName());
                room.addPlayer(player.getName());
                player.teleport(room.getLobby());
                break;
            }
            case GAME: {
                plugin.getPlayerData().set(player.getName() + ".type", type.name());
                player.teleport(room.getSpawn());
                break;
            }
            case NONE: {
                player.teleport(Utli.stringToLocation(plugin.getPlayerData().getString(player.getName() + ".location")));
                plugin.getPlayerData().set(player.getName(), null);
                room.removePlayer(player.getName());
                break;
            }
        }
        plugin.savePlayerData();
        return true;
    }

    public static RoomData getRoom(String playerName) {
        if (getLocationType(playerName) == NONE) return null;
        return RoomData.rooms.get(plugin.getPlayerData().getString(playerName + ".room"));
    }
}
