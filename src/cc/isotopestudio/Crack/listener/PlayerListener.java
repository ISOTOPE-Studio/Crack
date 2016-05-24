package cc.isotopestudio.Crack.listener;

import cc.isotopestudio.Crack.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Mars on 5/21/2016.
 * Copyright ISOTOPE Studio
 */
class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String name = event.getPlayer().getName();
        PlayerData.resetPlayer(name);
        if (PlayerData.getRoom(name) != null) {
            switch (PlayerData.getLocationType(name)) {
                case LOBBY: {

                    break;
                }
                case GAME: {

                    break;
                }
                case NONE:
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        PlayerData.resetPlayer(name);
        if (PlayerData.getRoom(name) != null) {
            switch (PlayerData.getLocationType(name)) {
                case LOBBY: {

                    break;
                }
                case GAME: {

                    break;
                }
                case NONE:
                    break;
            }
        }
    }
    
}
