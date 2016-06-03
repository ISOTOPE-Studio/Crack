package cc.isotopestudio.Crack.listener;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/21/2016.
 * Copyright ISOTOPE Studio
 */
public class ListenerManager {

    public static void enable() {
        HandlerList.unregisterAll(plugin);

        PlayerListener playerListener = new PlayerListener();
        MobListener mobListener = new MobListener();
        ProtectionListener blockListener = new ProtectionListener();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(playerListener, plugin);
        pm.registerEvents(mobListener, plugin);
        pm.registerEvents(blockListener, plugin);

    }
}
