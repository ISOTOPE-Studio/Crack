package cc.isotopestudio.Crack.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.utli.S;

public class CrackAdminCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("crackadmin")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(S.toPrefixRed("必须要玩家才能执行"));
				sendHelp(sender, label);
				return true;
			}
			Player player = (Player) sender;
			if (!player.hasPermission("crack.admin")) {
				sender.sendMessage(S.toPrefixRed("你没有权限"));
				return true;
			}
			if (args.length > 1 && !args[0].equalsIgnoreCase("help")) {
				if (args[1].equalsIgnoreCase("spawn")) {
					new RoomData(args[0], false).setSpawn(player.getLocation());
					player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的出生点"));
					return true;
				}
				if (args[1].equalsIgnoreCase("respawn")) {
					new RoomData(args[0], false).setRespawn(player.getLocation());
					player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的重生点"));
					return true;
				}
				if (args[1].equalsIgnoreCase("lobby")) {
					new RoomData(args[0], false).setLobby(player.getLocation());
					player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的等待大厅"));
					return true;
				}
				sendHelp(player, label);
				return true;
			} else {
				sendHelp(player, label);
				return true;
			}
		}
		return false;
	}

	void sendHelp(CommandSender player, String label) {
		player.sendMessage(S.toPrefixGreen("帮助菜单"));
		player.sendMessage(S.toBoldGreen("/" + label + " <房间名> spawn") + S.toGray(" - ") + S.toGold("设置副本房间出生点"));
		player.sendMessage(S.toBoldGreen("/" + label + " <房间名> respawn") + S.toGray(" - ") + S.toGold("设置副本房间重生点"));
		player.sendMessage(S.toBoldGreen("/" + label + " <房间名> lobby") + S.toGray(" - ") + S.toGold("设置副本房间大厅"));
	}

}
