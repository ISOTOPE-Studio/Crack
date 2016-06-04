package cc.isotopestudio.Crack.gui;

import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.data.PlayerData;
import cc.isotopestudio.Crack.type.LocationType;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CrackGUI extends GUI {
    public CrackGUI() {
        super(getName(S.toAqua("副本房间")), 9 * 6);
        int count = 0;
        for (Room room : Room.rooms.values()) {
            if (count > 9 * 6) break;
            if (!room.isFinish()) {
                continue;
            }
            switch (room.getStatus()) {
                case WAITING: {
                    setOption(count, new ItemStack(Material.WOOL, 1, (short) 5), S.toAqua(room.getName()), room.getStatus().toString(),
                            S.toYellow(room.getPlayerNum() + "个玩家"), S.toGreen("点击加入"), S.toYellow(room.getPlayersNames().toString()));
                    break;
                }
                case PROGRESS: {
                    setOption(count, new ItemStack(Material.WOOL, 1, (short) 1), S.toAqua(room.getName()), room.getStatus().toString(),
                            S.toYellow(room.getPlayerNum() + "个玩家"), S.toGreen("点击加入"), S.toYellow(room.getPlayersNames().toString()));
                    break;
                }
                case BOSS: {
                    setOption(count, new ItemStack(Material.WOOL, 1, (short) 14), S.toAqua(room.getName()), room.getStatus().toString(),
                            S.toYellow(room.getPlayerNum() + "个玩家"), S.toGreen("点击加入"), S.toYellow(room.getPlayersNames().toString()));
                    break;
                }
            }
            count++;
        }
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
            Player player = (Player) event.getWhoClicked();
            if (optionIcons[slot] == null) {
                player.sendMessage(S.toPrefixRed("副本在游戏中"));
                return;
            }
            Room room = Room.rooms.get(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            if (room.getStatus() != RoomStatus.WAITING) {

                return;
            }
            OptionClickEvent e = new OptionClickEvent(player, slot, optionNames[slot]);

            LocationType type = PlayerData.getLocationType(player.getName());
            if (type == LocationType.NONE) {
                PlayerData.teleport(player, room
                        ,
                        LocationType.LOBBY);
                player.sendMessage(S.toPrefixGreen("传送到游戏大厅"));
            }
            if (e.willClose()) {
                player.closeInventory();
            }
        }
    }


}
