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
				sender.sendMessage(S.toPrefixRed("����Ҫ��Ҳ���ִ��"));
				sendHelp(sender, label);
				return true;
			}
			Player player = (Player) sender;
			if (!player.hasPermission("crack.admin")) {
				sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
				return true;
			}
			if (args.length > 1 && !args[0].equalsIgnoreCase("help")) {
				if (args[1].equalsIgnoreCase("spawn")) {
					new RoomData(args[0], false).setSpawn(player.getLocation());
					player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " �ĳ�����"));
					return true;
				}
				if (args[1].equalsIgnoreCase("respawn")) {
					new RoomData(args[0], false).setRespawn(player.getLocation());
					player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " ��������"));
					return true;
				}
				if (args[1].equalsIgnoreCase("lobby")) {
					new RoomData(args[0], false).setLobby(player.getLocation());
					player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " �ĵȴ�����"));
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
		player.sendMessage(S.toPrefixGreen("�����˵�"));
		player.sendMessage(S.toBoldGreen("/" + label + " <������> spawn") + S.toGray(" - ") + S.toGold("���ø������������"));
		player.sendMessage(S.toBoldGreen("/" + label + " <������> respawn") + S.toGray(" - ") + S.toGold("���ø�������������"));
		player.sendMessage(S.toBoldGreen("/" + label + " <������> lobby") + S.toGray(" - ") + S.toGold("���ø����������"));
	}

}
