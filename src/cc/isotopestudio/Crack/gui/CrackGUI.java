package cc.isotopestudio.Crack.gui;

import cc.isotopestudio.Crack.data.PlayerData;
import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.type.LocationType;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CrackGUI extends GUI {
    public CrackGUI() {
        super(getName(S.toAqua("��������")), 9 * 6);
        int count = 0;
        for (RoomData room : RoomData.rooms.values()) {
            if (count > 9 * 6) break;
            if (!room.isFinish()) {
                continue;
            }
            setOption(count, new ItemStack(Material.DIAMOND_SWORD), S.toAqua(room.getName()),
                    S.toYellow(room.getPlayerNum() + "�����"), S.toGreen("�������"));
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
            if (optionIcons[slot] == null) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            OptionClickEvent e = new OptionClickEvent(player, slot, optionNames[slot]);

            LocationType type = PlayerData.getLocationType(player);
            if (type == LocationType.NONE) {
                PlayerData.teleport(player,
                        RoomData.rooms.get(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())),
                        LocationType.LOBBY);
                player.sendMessage(S.toPrefixGreen("���͵���Ϸ����"));
            } else {
                player.sendMessage(S.toPrefixGreen("������Ϸ��"));
            }
            if (e.willClose()) {
                player.closeInventory();
            }
        }
    }


}
