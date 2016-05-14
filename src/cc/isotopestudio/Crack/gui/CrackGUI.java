package cc.isotopestudio.Crack.gui;

import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * Created by Mars on 5/14/2016.
 */
public class CrackGUI extends GUI {
    public CrackGUI() {
        super(getName(S.toAqua("副本房间")), 9);
        Set<String> rooms = plugin.getRoomData().getKeys(false);
        int count = 0;
        for (String room : rooms) {
            if (count > 8) break;
            RoomData data = new RoomData(room);
            if (!data.isFinish()) {
                continue;
            }
            setOption(count, new ItemStack(Material.DIAMOND_SWORD), S.toAqua(data.getName()), S.toGreen("点击加入"));
            count++;
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getInventory().getName().equals(name)){
            event.setCancelled(true);
        }
    }
}
