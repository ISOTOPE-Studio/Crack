package cc.isotopestudio.Crack.gui;

import cc.isotopestudio.Crack.data.PlayerData;
import cc.isotopestudio.Crack.Room.Room;
import cc.isotopestudio.Crack.type.LocationType;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GameGUI extends GUI {
    private final Room room;

    public GameGUI(Room room) {
        super(getName(S.toAqua("副本房间")), 9);
        this.room = room;
        setOption(0, new ItemStack(Material.DIAMOND_SWORD), S.toAqua(room.getName()),
                S.toYellow(room.getPlayerNum() + "个玩家"), S.toRed("点击退出"));
    }

    @Override
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getInventory().getName().equals(name)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot < 0 || slot >= size) {
                return;
            }
            if (optionIcons[slot] == null) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            PlayerData.teleport(player, room, LocationType.NONE);
            player.sendMessage(S.toPrefixGreen("退出竞技场"));
            player.closeInventory();
        }
    }

}
