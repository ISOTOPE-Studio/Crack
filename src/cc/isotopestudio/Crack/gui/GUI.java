package cc.isotopestudio.Crack.gui;

import cc.isotopestudio.Crack.Crack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public abstract class GUI implements Listener {

    // From: https://bukkit.org/threads/icon-menu.108342

    protected final String name;
    protected final int size;
    protected OptionClickEventHandler[] handler;
    protected Crack plugin;
    protected String[] optionNames;
    protected ItemStack[] optionIcons;
    protected int page;
    protected HashMap<Integer, Integer> slotIDMap;
    protected boolean isDestoryed = false;

    public GUI(String name, int size) {
        this.name = name;
        this.size = size;
        this.plugin = Crack.plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GUI setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }

    public GUI setOption(int position, ItemStack item) {
        optionNames[position] = item.getItemMeta() == null ? item.getType().toString()
                : item.getItemMeta().getDisplayName();
        optionIcons[position] = item;
        return this;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }

    public void Destory() {
        isDestoryed = true;
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }

    public void setHandlerList(OptionClickEventHandler[] handler) {
        this.handler = handler;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(final InventoryClickEvent event) {
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    Destory();
                }
            }, 0);
        }
    }

    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

    public static String getName(String a) {
        for (int i = 0; i <= 5; i++) {
            switch ((int) (Math.random() * 5)) {
                case (0): {
                    a += "¡ìf";
                    break;
                }
                case (1): {
                    a += "¡ì1";
                    break;
                }
                case (2): {
                    a += "¡ì2";
                    break;
                }
                case (3): {
                    a += "¡ì3";
                    break;
                }
                case (4): {
                    a += "¡ì4";
                    break;
                }
            }
        }
        return a;
    }

    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);
    }

    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;

        public OptionClickEvent(Player player, int position, String name) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = false;
            this.destroy = false;
        }

        public Player getPlayer() {
            return player;
        }

        public int getPosition() {
            return position;
        }

        public String getName() {
            return name;
        }

        public boolean willClose() {
            return close;
        }

        public boolean willDestroy() {
            return destroy;
        }

        public void setWillClose(boolean close) {
            this.close = close;
        }

        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
    }
}