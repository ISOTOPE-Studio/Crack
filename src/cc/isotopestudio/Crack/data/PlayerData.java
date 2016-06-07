package cc.isotopestudio.Crack.data;

import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.type.LocationType;
import cc.isotopestudio.Crack.utli.S;
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
        Room room = getRoom(playerName);
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

    public static boolean teleport(Player player, Room room, LocationType type) {
        if (!room.isFinish()) return false;
        switch (type) {
            case LOBBY: {
                plugin.getPlayerData().set(player.getName() + ".location", Utli.locationToString(player.getLocation()));
                plugin.getPlayerData().set(player.getName() + ".type", type.name());
                plugin.getPlayerData().set(player.getName() + ".room", room.getName());
                Utli.sendAllPlayers(room, S.toPrefixGreen(player.getName()+" 加入房间"));
                room.addPlayer(player.getName());
                player.teleport(room.getLobby());
                break;
            }
            case GAME: {
                plugin.getPlayerData().set(player.getName() + ".type", type.name());
                player.teleport(room.getSpawn());
                break;
            }
            case RESPAWN: {
                plugin.getPlayerData().set(player.getName() + ".type", type.name());
                player.teleport(room.getRespawn());
                Utli.sendAllPlayers(room, S.toPrefixRed(player.getName()+" 等待复活"));
                break;
            }
            case BOSS: {
                plugin.getPlayerData().set(player.getName() + ".type", type.name());
                player.teleport(room.getBossObj().getLocation());
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

    public static Room getRoom(String playerName) {
        if (getLocationType(playerName) == NONE) return null;
        return Room.rooms.get(plugin.getPlayerData().getString(playerName + ".room"));
    }
}
