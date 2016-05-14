package cc.isotopestudio.Crack.command;

import cc.isotopestudio.Crack.gui.CrackGUI;
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
                sender.sendMessage(S.toPrefixRed("必须要玩家才能执行"));
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("crack.gui")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }
            new CrackGUI().open(player);
            return true;
        }
        return false;
    }

}
