package cc.isotopestudio.Crack.command;

import cc.isotopestudio.Crack.data.PlayerData;
import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.gui.CrackGUI;
import cc.isotopestudio.Crack.gui.GameGUI;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("crack")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("����Ҫ��Ҳ���ִ��"));
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("crack.gui")) {
                sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
                return true;
            }
            RoomData room = PlayerData.getRoom(player.getName());
            if (room == null)
                new CrackGUI().open(player);
            else
                new GameGUI(room).open(player);
            return true;
        }
        return false;
    }

}
